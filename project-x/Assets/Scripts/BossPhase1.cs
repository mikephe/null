using UnityEngine;
using System.Collections;

public class BossPhase1 : Enemy {
    public bool dealDamageOnCollision = true;
    public float collisionDamage = 10;
    public float knockback = 2000;
    public float stutterFactor = 500;
    public bool rotate = true;
    public bool chaseTarget = true;

    public GameObject refBullet;
    public float projectileSpeed = 5;
    public float COOLDOWNTIME = 1f;
    public float range = 7;
    private float timeSinceLastFired = 0f;
    private Transform playerTransform;
    public GameObject phase2;
    // Use this for initialization
    void Start()
    {
        EnemyStart();
    }

    // Update is called once per frame
    void Update()
    {
        if (target != null)
        {
            EnemyUpdate();
            EnemyMovement();
            rangedUpdate();
            Vector3 targetPos = Camera.main.WorldToScreenPoint(target.transform.position);

            Vector3 objectPos = Camera.main.WorldToScreenPoint(transform.position);
            targetPos.x = targetPos.x - objectPos.x;
            targetPos.y = targetPos.y - objectPos.y;
            if (rotate)
            {
                float angle = Mathf.Atan2(targetPos.y, targetPos.x) * Mathf.Rad2Deg;
                transform.rotation = Quaternion.Euler(new Vector3(0, 0, angle + 270));
            }
        }
        else {
            if (Utilities.getPlayerTransform() != null)
            {
                target = Utilities.getPlayerTransform();
            }
        }
    }

    void Chasing()
    {
        float Xdif = target.position.x - transform.position.x;
        float Ydif = target.position.y - transform.position.y;
        Vector2 Playerdirection = new Vector2(Xdif, Ydif);
        r.velocity = (Playerdirection.normalized * walkVel);
        r.AddForce(new Vector2(Random.Range(-stutterFactor, stutterFactor), Random.Range(-stutterFactor, stutterFactor)));
    }

    public override void EnemyMovement()
    {
        if (target != null && chaseTarget)
        {
            Chasing();
        }
        else {
            r.velocity = new Vector2(0, 0);
        }
    }

    void OnCollisionEnter2D(Collision2D coll)
    {
        if (coll.gameObject.CompareTag("Player") && dealDamageOnCollision)
        {
            coll.gameObject.GetComponent<Player>().damageEntity(collisionDamage);
            float verticalPush = coll.gameObject.transform.position.y - transform.position.y;
            float horizontalPush = coll.gameObject.transform.position.x - transform.position.x;

            coll.gameObject.GetComponent<Rigidbody2D>().AddForce(new Vector2(horizontalPush, verticalPush) * knockback);
            GetComponent<Rigidbody2D>().AddForce(new Vector2(-horizontalPush, -verticalPush) * knockback);
        }
    }



    bool findTarget()
    {
        playerTransform = GameObject.FindGameObjectWithTag("Player").gameObject.transform;
        Vector3 distance = playerTransform.position - transform.position;

        if (Mathf.Abs(distance.x) < range && Mathf.Abs(distance.y) < range)
        {
            return true;
        }

        return false;
    }

    void rangedUpdate()
    {

        if (findTarget() && canFire())
        {
            rangedAttack();
        }
    }

    private bool canFire()
    {
        if (timeSinceLastFired > COOLDOWNTIME)
        {
            timeSinceLastFired = 0f;
            return true;
        }
        else
        {
            timeSinceLastFired += Time.deltaTime;
            return false;
        }
    }

    private void rangedAttack()
    {
        if (refBullet == null) return;
        GameObject clonedesu = (GameObject)Instantiate(refBullet, transform.position, Quaternion.identity);
        projectileTrajectory(clonedesu);
        Destroy(clonedesu, 2);
    }

    Vector3 projectileTrajectory(GameObject clone)
    {
        Vector2 Playerdirection;
        float Xdif = playerTransform.position.x - transform.position.x;
        float Ydif = playerTransform.position.y - transform.position.y;

        Playerdirection = new Vector2(Xdif, Ydif);
        clone.GetComponent<Rigidbody2D>().velocity = (Playerdirection.normalized * projectileSpeed);
        float angle = Mathf.Atan2(playerTransform.position.y, playerTransform.position.x) * Mathf.Rad2Deg;
        clone.transform.rotation = Quaternion.Euler(new Vector3(0, 0, angle - 90));

        return refBullet.GetComponent<Rigidbody2D>().velocity;
    }

    void spawnBoss()
    {
        GameObject boss = (GameObject)Instantiate(phase2, transform.position, Quaternion.identity);
    }

    override public void deathCheck()
    {
        if (!deathState)
        { //check to see if they are already in death state
            if (health <= 0)
            {
                deathState = true;
                Utilities.score += scoreBounty;
                if (gameObject.CompareTag("Enemy"))
                {
                    Utilities.enemyCount -= 1;
                    if (Random.Range(0, 100) < dropRate && healthDrop != null)
                    {
                        Instantiate(healthDrop, transform.position, Quaternion.identity);
                    }
                }
                if (deathExplosion != null)
                {
                    GameObject explosion = (GameObject)Instantiate(deathExplosion, transform.position, Quaternion.identity);
                    Destroy(explosion, 0.2f);
                }
                if (deathNoise != null)
                {
                    audio.PlayOneShot(deathNoise, 1);
                }
                Invoke("spawnBoss", deathCounter - 0.1f);
                Destroy(gameObject, deathCounter);
                //gameObject.collider2D.enabled = false;
            }
        }
    }
}
