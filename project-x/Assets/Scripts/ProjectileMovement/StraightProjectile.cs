using UnityEngine;
using System.Collections;

public class StraightProjectile : ProjectileMovement {

	public override void movement (float angle, Vector3 dir)
	{


        Vector3 origin = new Vector2(transform.position.x, transform.position.y + yOffset);
        //clones prefab
        GameObject clone = (GameObject) Instantiate(bullet, origin + dir * firingDistance, Quaternion.identity); 
		clone.transform.rotation = Quaternion.Euler(new Vector3(0, 0, angle));

		//subtract target position and current position to get vector
		clone.GetComponent<Rigidbody2D> ().velocity = new Vector3 (dir.x * speed, dir.y * speed, 0);
        //set velocity of cloned object so it moves towards target along calculated vector
        muzzleFlash();
		Destroy (clone, lifeSpan);
	}
}
