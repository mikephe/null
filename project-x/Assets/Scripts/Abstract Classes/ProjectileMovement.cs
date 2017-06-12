using UnityEngine;
using System.Collections;

public abstract class ProjectileMovement : MonoBehaviour {
	public GameObject bullet; //bullet prefab, drag onto script
    public GameObject muzzle;
    public float firingDistance = 0.5f;
    public float xOffset = 0;
    public float yOffset = 0;
    public int speed = 10; //default projectile speed
	public float lifeSpan = 1;
	public float shotCooldown = .1f;
	public float hitTime;
    public AudioClip sfx;
	public abstract void movement (float angle, Vector3 dir);

    protected void muzzleFlash()
    {
        if (muzzle != null && muzzle.GetComponent<SpriteRenderer>().enabled == false)
        {
            muzzle.GetComponent<SpriteRenderer>().enabled = true;
        }
        Invoke("turnOffFlash", 0.1f);
    }

    protected void turnOffFlash()
    {
        if (muzzle != null && muzzle.GetComponent<SpriteRenderer>().enabled == true)
        {
            muzzle.GetComponent<SpriteRenderer>().enabled = false;
        }
    }
}
