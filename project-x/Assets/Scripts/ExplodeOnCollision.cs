using UnityEngine;
using System.Collections;
using System;

public class ExplodeOnCollision : ProjectileProperty {
    public GameObject explosion;
    public float lifeSpanAfterTargetCollision = 0.2f;
    public float lifeSpanAfterMiscCollision = 0.2f;
    public float explosionLifeSpan = 0.2f;
    protected AudioSource audio;
    public AudioClip explosionFx;
    void Start()
    {
        audio = GetComponent<AudioSource>();
    }
    public override void property()
    {
        //e.damageEntity(damage);
        if (GetComponent<Entity>() != null)
        {
            GetComponent<Entity>().health = 0;
        }
        else {
            Destroy(gameObject, lifeSpanAfterTargetCollision);
        }
    }

    void spawnExplosion()
    {
        GameObject clone1 = (GameObject)Instantiate(explosion, transform.position, Quaternion.identity);
        if (explosionFx != null)
            audio.PlayOneShot(explosionFx, 1);
        Destroy(clone1, explosionLifeSpan);
    }

    void OnCollisionEnter2D(Collision2D collInfo)
    {
        if (collInfo.gameObject.tag.Equals(target.ToString()))
        {
            e = collInfo.gameObject.GetComponent<Entity>();
            property();
            spawnExplosion();
        }
        else if (!collInfo.gameObject.tag.Equals("Enemy") && !collInfo.gameObject.tag.Equals("Player") && !collInfo.gameObject.tag.Equals("Projectile"))
        {
            spawnExplosion();
            if (GetComponent<Entity>() != null)
            {
                GetComponent<Entity>().health = 0;
            }
            else {
                Destroy(gameObject, lifeSpanAfterMiscCollision);
            }
        }
    }

    void OnTriggerEnter2D(Collider2D collInfo)
    {
        if (collInfo.gameObject.tag.Equals(target.ToString()))
        {
            e = collInfo.gameObject.GetComponent<Entity>();
            property();
        }
        else {
            Destroy(gameObject, lifeSpanAfterMiscCollision);
        }
    }
}
