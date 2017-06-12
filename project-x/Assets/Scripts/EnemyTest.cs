﻿using UnityEngine;
using System.Collections;

public class EnemyTest : Enemy {
    public bool dealDamageOnCollision = true;
	public float collisionDamage = 10;
	public float knockback = 2000;
	public float stutterFactor = 500;
	public bool rotate = true;
	public bool chaseTarget = true;
	// Use this for initialization
	void Start () {
		EnemyStart ();
	}
	
	// Update is called once per frame
	void Update () {
		if (target != null) {
			EnemyUpdate ();
			EnemyMovement ();

			Vector3 targetPos = Camera.main.WorldToScreenPoint (target.transform.position);
		
			Vector3 objectPos = Camera.main.WorldToScreenPoint (transform.position);
			targetPos.x = targetPos.x - objectPos.x;
			targetPos.y = targetPos.y - objectPos.y;
			if (rotate) {
				float angle = Mathf.Atan2 (targetPos.y, targetPos.x) * Mathf.Rad2Deg;
				transform.rotation = Quaternion.Euler (new Vector3 (0, 0, angle + 270));
			}
		} else {
			if (Utilities.getPlayerTransform() != null) {
				target = Utilities.getPlayerTransform();
			}
		}
	}

	void Chasing ()
	{
		float Xdif = target.position.x - transform.position.x;
		float Ydif = target.position.y - transform.position.y;
		Vector2 Playerdirection = new Vector2 (Xdif, Ydif);
		r.velocity = (Playerdirection.normalized * walkVel);
		r.AddForce(new Vector2(Random.Range(-stutterFactor, stutterFactor), Random.Range(-stutterFactor, stutterFactor)));
	}

	public override void EnemyMovement ()
	{
		if (target != null && chaseTarget) {
			Chasing ();
		} else {
			r.velocity = new Vector2(0, 0);
		}
	}

	void OnCollisionEnter2D(Collision2D coll) {
		if (coll.gameObject.CompareTag ("Player") && dealDamageOnCollision) {
			coll.gameObject.GetComponent<Player>().damageEntity(collisionDamage);
			float verticalPush = coll.gameObject.transform.position.y - transform.position.y;
			float horizontalPush = coll.gameObject.transform.position.x - transform.position.x;

			coll.gameObject.GetComponent<Rigidbody2D>().AddForce(new Vector2(horizontalPush, verticalPush) * knockback);
			GetComponent<Rigidbody2D>().AddForce(new Vector2(-horizontalPush, -verticalPush) * knockback);
		}
	}
}
