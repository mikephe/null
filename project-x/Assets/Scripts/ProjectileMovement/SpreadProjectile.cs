using UnityEngine;
using System.Collections;

public class SpreadProjectile : ProjectileMovement {
	public float strayFactor = .15f;
	public float randomFactor = 0;
	public float bulletFactor = 1;
    public float angleRotation;
    private Vector3 dir;

	public override void movement (float angle, Vector3 dir)
	{
        angleRotation = angle;
        this.dir = dir;
        //get position relative to camera
        Vector3 origin = new Vector2(transform.position.x, transform.position.y + yOffset);

		//subtract target position and current position to get vector
		GameObject clone = (GameObject) Instantiate(bullet, origin + dir * firingDistance, Quaternion.identity);
        clone.transform.rotation = Quaternion.Euler(new Vector3(0, 0, angleRotation));
        //clones prefab
        clone.GetComponent<Rigidbody2D> ().velocity = new Vector3 ((dir.x + Random.Range(-randomFactor, randomFactor)) * speed, 
		(dir.y + Random.Range(-randomFactor, randomFactor)) * speed); 
		float bulletSpreadFactor = .05f;
		for (int i = 0; i <= bulletFactor; i++) {
			createSpreadPair (strayFactor + (i * bulletSpreadFactor), randomFactor);
		}
        //Debug.Log ("Angle: " + angle);

        //set velocity of cloned object so it moves towards target along calculated vector
        muzzleFlash();
		Destroy (clone, lifeSpan);
	}



	private void createSpreadPair(float stray, float rand) {
        //subtract target position and current position to get vector
        Vector3 origin = new Vector2(transform.position.x, transform.position.y + yOffset);
        GameObject clone1 = (GameObject) Instantiate(bullet, origin + dir * firingDistance, Quaternion.identity);
        
        GameObject clone2 = (GameObject) Instantiate(bullet, origin + dir * firingDistance, Quaternion.identity);
        
        //clones prefab
        float angle = Mathf.Atan2(dir.y, dir.x);
        float degree = angle * Mathf.Rad2Deg;

        clone1.transform.rotation = Quaternion.Euler(new Vector3(0, 0, angleRotation));
        clone2.transform.rotation = Quaternion.Euler(new Vector3(0, 0, angleRotation));

        clone1.GetComponent<Rigidbody2D> ().velocity = 
			new Vector3 (speed * Mathf.Cos(angle + strayFactor + 
			Random.Range(-randomFactor, randomFactor)), speed * Mathf.Sin(angle + strayFactor + 
			Random.Range(-randomFactor, randomFactor)), 0); 
		clone2.GetComponent<Rigidbody2D> ().velocity = 
			new Vector3 (speed * Mathf.Cos(angle - strayFactor - 
			Random.Range(-randomFactor, randomFactor)), speed * Mathf.Sin(angle - strayFactor -
			Random.Range(-randomFactor, randomFactor)), 0); 
		Destroy (clone1, lifeSpan);
		Destroy (clone2, lifeSpan);
	}
}
