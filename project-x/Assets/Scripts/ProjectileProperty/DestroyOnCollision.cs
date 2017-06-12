using UnityEngine;
using System.Collections;

public class DestroyOnCollision : ProjectileProperty {
	public float damage = 10;
	public float lifeSpanAfterTargetCollision = 0.2f;
    public float lifeSpanAfterMiscCollision = 0.2f;
	public override void property() {
		//e.damageEntity(damage);
		Destroy (gameObject, lifeSpanAfterTargetCollision);
	}

	void OnCollisionEnter2D(Collision2D collInfo) {
		if (collInfo.gameObject.tag.Equals(target.ToString())) {
			e = collInfo.gameObject.GetComponent<Entity> ();
            e.damageEntity(damage);
			property();
		} else {
			Destroy (gameObject, lifeSpanAfterMiscCollision);
		}
	}

    void OnTriggerEnter2D(Collider2D collInfo)
    {
        if (collInfo.gameObject.tag.Equals(target.ToString()))
        {
            e = collInfo.gameObject.GetComponent<Entity>();
            e.damageEntity(damage);
            property();
        }
        else {
            Destroy(gameObject, lifeSpanAfterMiscCollision);
        }
    }

}
