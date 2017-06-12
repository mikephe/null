using UnityEngine;
using System.Collections;

public class CameraFollow : MonoBehaviour {
    public bool position = false;
    public bool velocity = false;
    public Transform target;
    public float xOffset;
    public float yOffset;
	// Use this for initialization
	void Start () {
	
	}

    // Update is called once per frame
    void Update() {
        if (target != null) {
            if (position)
                positionFollow();
            if (velocity)
                velocityFollow();
        }
	}

    void positionFollow()
    {
        gameObject.transform.position = new Vector3(target.transform.position.x + xOffset, target.transform.position.y + yOffset, transform.position.z);
    }

    void velocityFollow()
    {

        gameObject.GetComponent<Rigidbody2D>().velocity = new Vector2(target.GetComponent<Rigidbody2D>().velocity.x, target.GetComponent<Rigidbody2D>().velocity.y);
    }
}
