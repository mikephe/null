using UnityEngine;
using System.Collections;
using System;

public class Turret : Enemy {
    public float collisionDamage = 10;
    public float knockback = 5000;
    public GameObject refBullet;
    public float COOLDOWNTIME = 1f;
    public float projectileSpeed = 20;
    public float range = 7;
    private float timeSinceLastFired = 0f;
    public float stutterFactor = 500;
    private Transform playerTransform;
    public bool chaseTarget = false;

    // Use this for initialization
    void Start () {
        EnemyStart();
	}

    override protected void playerMaterial()
    {
        /*
		 * call to update after getting hit so that we go back to default material,
		 */
        if ((hitTime + 0.1f < Time.time))
        {
            foreach (SpriteRenderer s in gameObject.GetComponentsInChildren<SpriteRenderer>())
            {
                s.material = defMat;
            }
        }

        if (hitTime + 0.1f >= Time.time)
        {
            //Debug.Log("HIT");
            foreach (SpriteRenderer s in gameObject.GetComponentsInChildren<SpriteRenderer>())
            {
                s.material = hitMat;
            }
        }
    }

    // Update is called once per frame
    void Update () {
        EntityUpdate();
        if (target != null)
        {
            rangedUpdate();
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
        clone.transform.rotation = Quaternion.Euler(new Vector3(0, 0, angle + 135));
        //Debug.Log(angle);

        return refBullet.GetComponent<Rigidbody2D>().velocity;
    }

    public override void EnemyMovement()
    {
        throw new NotImplementedException();
    }
}
