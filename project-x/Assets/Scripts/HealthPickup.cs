using UnityEngine;
using System.Collections;

public class HealthPickup : MonoBehaviour {
    public float health;
	void OnCollisionStay2D(Collision2D coll) {
		if (coll.gameObject.CompareTag ("Player")) {
			coll.gameObject.GetComponent<Player>().health += health;
			Destroy(gameObject);
		}
	}
}
