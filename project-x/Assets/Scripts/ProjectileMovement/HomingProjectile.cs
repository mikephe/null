using UnityEngine;
using System.Collections;

public class HomingProjectile : ProjectileMovement {
	public override void movement (float angle, Vector3 dir)
	{
		//clones prefab
		GameObject clone = (GameObject) Instantiate(bullet, transform.position + dir * firingDistance, Quaternion.identity); 
		clone.transform.rotation = Quaternion.Euler(new Vector3(0, 0, angle-90));

		//subtract target position and current position to get vector
		clone.GetComponent<Rigidbody2D> ().velocity = new Vector3 (dir.x * speed, dir.y * speed, 0); 
		//set velocity of cloned object so it moves towards target along calculated vector
		Destroy (clone, lifeSpan);
	}


}
