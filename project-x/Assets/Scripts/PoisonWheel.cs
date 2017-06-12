using UnityEngine;
using System.Collections;
using System;

public class PoisonWheel : Enemy {
    public float collisionDamage = 10;
    public float knockback = 2000;
    public bool rotate = true;
    public LayerMask lm;
    private int dir = 1;

    public override void EnemyMovement()
    {
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

    // Use this for initialization
    void Start () {
        EnemyStart();
	}
	
	// Update is called once per frame
	void Update () {
        EnemyMovement();
        EnemyUpdate();
        GroundCheck();
	}

    void OnCollisionEnter2D(Collision2D coll)
    {
        if (coll.gameObject.CompareTag("Player"))
        {
            coll.gameObject.GetComponent<Player>().damageEntity(collisionDamage);
            float verticalPush = coll.gameObject.transform.position.y - transform.position.y;
            float horizontalPush = coll.gameObject.transform.position.x - transform.position.x;

            coll.gameObject.GetComponent<Rigidbody2D>().AddForce(new Vector2(horizontalPush, verticalPush) * knockback);
            GetComponent<Rigidbody2D>().AddForce(new Vector2(-horizontalPush, -verticalPush) * knockback);
        }
    }

}
