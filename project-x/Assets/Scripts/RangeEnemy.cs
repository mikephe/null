using UnityEngine;
using System.Collections;

public class RangeEnemy : Enemy {

	public float collisionDamage = 10;
	public float knockback = 5000;
	public GameObject refBullet;
    public float projectileSpeed = 5;
	public float COOLDOWNTIME = 1f;
	public float range = 7;
	private float timeSinceLastFired = 0f;
	public float stutterFactor = 500;
	private Transform playerTransform;
    public LayerMask lm;
    private int dir = 1;
    public bool chaseTarget = false;
    public bool patrol = false;
	// Use this for initialization
	void Start () {
		EnemyStart ();

	}
	
	// Update is called once per frame
	void Update () {
		if (target != null) {
			EnemyUpdate ();
			EnemyMovement ();
            directionCheck();
			rangedUpdate ();
		}
	}

    void directionCheck()
    {
        if (Utilities.gravityDir.Equals(GravityDirection.South))
        {
            transform.rotation = Quaternion.Euler(new Vector3(0, 0, 0));
            if (r.velocity.x >= 0)
                transform.localScale = new Vector2(-1, 1);
            else
                transform.localScale = new Vector2(1, 1);
        }
        if (Utilities.gravityDir.Equals(GravityDirection.North))
        {
            transform.rotation = Quaternion.Euler(new Vector3(0, 0, 0));
            if (r.velocity.x >= 0)
                transform.localScale = new Vector2(-1, -1);
            else
                transform.localScale = new Vector2(1, -1);
        }
        if (Utilities.gravityDir.Equals(GravityDirection.West))
        {
            transform.rotation = Quaternion.Euler(new Vector3(0, 0, 90));
            if (r.velocity.x >= 0)
                transform.localScale = new Vector2(1, -1);
            else
                transform.localScale = new Vector2(1, 1);
        }
        if (Utilities.gravityDir.Equals(GravityDirection.East))
        {
            transform.rotation = Quaternion.Euler(new Vector3(0, 0, 90));
            if (r.velocity.x >= 0)
                transform.localScale = new Vector2(-1, -1);
            else
                transform.localScale = new Vector2(-1, 1);
        }
    }

    void GroundCheck()
    {
        RaycastHit2D hit = Physics2D.Raycast(gameObject.transform.position, new Vector2(0, -1), 0.5f, lm);
        if (Utilities.gravityDir.Equals(GravityDirection.North))
            hit = Physics2D.Raycast(gameObject.transform.position, new Vector2(0, 1), 0.5f, lm);
        if (Utilities.gravityDir.Equals(GravityDirection.East))
            hit = Physics2D.Raycast(gameObject.transform.position, new Vector2(1, 0), 0.5f, lm);
        if (Utilities.gravityDir.Equals(GravityDirection.West))
            hit = Physics2D.Raycast(gameObject.transform.position, new Vector2(-1, 0), 0.5f, lm);

        if (hit.collider)
        {
            isGrounded = true;
        }
        else {
            isGrounded = false;
        }
    }

    void Patrol() {
        GroundCheck();
        if (isGrounded)
        {
            if (Utilities.gravityDir.Equals(GravityDirection.South) || Utilities.gravityDir.Equals(GravityDirection.North))
            {
                r.velocity = new Vector2(walkVel * dir, 0);
                RaycastHit2D hit = Physics2D.Raycast(gameObject.transform.position, new Vector2(dir, 0), 0.5f, lm);
                if (hit.collider)
                {
                    dir *= -1;
                }
            }
            if (Utilities.gravityDir.Equals(GravityDirection.West) || Utilities.gravityDir.Equals(GravityDirection.East))
            {
                r.velocity = new Vector2(0, walkVel * dir);
                RaycastHit2D hit = Physics2D.Raycast(gameObject.transform.position, new Vector2(0, dir), 0.5f, lm);
                if (hit.collider)
                {
                    dir *= -1;
                }
            }
        }
    }

	void Chasing ()
	{
		float Xdif = target.position.x - transform.position.x;
		float Ydif = target.position.y - transform.position.y;
		Vector2 Playerdirection = new Vector2 (Xdif, Ydif);
		
		r.AddForce(new Vector2(Random.Range(-stutterFactor, stutterFactor), Random.Range(-stutterFactor, stutterFactor)));

        if (Utilities.gravityDir.Equals(GravityDirection.South) || Utilities.gravityDir.Equals(GravityDirection.North))
        {
            r.velocity = new Vector2((Playerdirection.normalized.x * walkVel), 0);
        }   
        if (Utilities.gravityDir.Equals(GravityDirection.East) || Utilities.gravityDir.Equals(GravityDirection.West))
        {
            r.velocity = new Vector2(0, (Playerdirection.normalized.y * walkVel));
        }
    }
	
	public override void EnemyMovement ()
	{
		if (target != null) {
            if (chaseTarget && findTarget())
			Chasing ();
            if (patrol)
                Patrol();
		} else {
			r.velocity = new Vector2(0, 0);
		}
	}

	bool findTarget() {
		playerTransform = GameObject.FindGameObjectWithTag ("Player").gameObject.transform;
		Vector3 distance = playerTransform.position - transform.position;
		
		if (Mathf.Abs (distance.x) < range  && Mathf.Abs(distance.y) < range) {
			return true;
		}

		return false;
	}

	void rangedUpdate() {

		if (findTarget () && canFire ()) {
			rangedAttack ();
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
		GameObject clonedesu = (GameObject) Instantiate(refBullet, transform.position, Quaternion.identity);  
		projectileTrajectory (clonedesu);
		Destroy (clonedesu,2);
	}
	
	Vector3 projectileTrajectory (GameObject clone)
	{
		Vector2 Playerdirection;
		float Xdif = playerTransform.position.x - transform.position.x;
		float Ydif = playerTransform.position.y - transform.position.y;

		Playerdirection = new Vector2 (Xdif, Ydif);
		clone.GetComponent<Rigidbody2D>().velocity = (Playerdirection.normalized * projectileSpeed);
		float angle = Mathf.Atan2(playerTransform.position.y, playerTransform.position.x) * Mathf.Rad2Deg;
		clone.transform.rotation = Quaternion.Euler(new Vector3(0, 0, angle-90));

		return refBullet.GetComponent<Rigidbody2D>().velocity;
	}

}
