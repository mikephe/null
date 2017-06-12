using UnityEngine;
using System.Collections;

public class DamageOverTime : MonoBehaviour {
	public float damage = 5;
    public EntityType type;
	void OnCollisionStay2D(Collision2D coll) {
		if (coll.gameObject.CompareTag (type.ToString())) {
			coll.gameObject.GetComponent<Entity>().damageEntity(damage);
		}
	}
}
