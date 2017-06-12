using UnityEngine;
using System.Collections;

public class MouseFollow : MonoBehaviour {
    public bool torso;
    public bool rotateToCursor = true;
    public bool rotateToTarget = false;
    public GameObject target;

	
	// Update is called once per frame
	void Update () {
        if (rotateToCursor)
        {
            rotateCharacterToCursor2(Utilities.calculateMouseAngle(transform));
            rotateCharacterToCursor2(Utilities.rightJoyAngle());
        }
        if (target != null && rotateToTarget)
        {
            rotateObjectToTarget();
        }
    }

    void rotateObjectToTarget()
    {
        Vector3 targetPos = Camera.main.WorldToScreenPoint(target.transform.position);
        Vector3 objectPos = Camera.main.WorldToScreenPoint(transform.position);
        targetPos.x = targetPos.x - objectPos.x;
        targetPos.y = targetPos.y - objectPos.y;

        float angle = Mathf.Atan2(targetPos.y, targetPos.x) * Mathf.Rad2Deg;
        //Debug.Log("Angle: " + angle);
        
        if (Utilities.gravityDir.Equals(GravityDirection.South))
        {
            if ((angle < 90 && angle > 0) || (angle < 0 && angle > -90))
            {
                transform.localScale = new Vector2(-1, 1);
                transform.rotation = Quaternion.Euler(new Vector3(0, 0, angle));
            } else
            {
                transform.rotation = Quaternion.Euler(new Vector3(0, 0, angle + 180));
                transform.localScale = new Vector2(1, 1);

            }
        }
    }

    void rotateCharacterToCursor(float angle)
    {
            if (Utilities.gravityDir.Equals(GravityDirection.South)) {
                calculateTorsoRotation(angle, 120, -120, -60, 60, 180, 0, -1, 1, 1, 1, true);
                calculateLegRotation(angle, 120, -120, 60, -60, -1, 1, 1, 1, true);
            }

            if (Utilities.gravityDir.Equals(GravityDirection.North))
            {
                calculateTorsoRotation(angle, 120, -120, -60, 60, 180, 0, -1, -1, 1, -1, true);
                calculateLegRotation(angle, 120, -120, 60, -60, 1, 1, -1, 1, true);
            }

            if (Utilities.gravityDir.Equals(GravityDirection.East))
            {
                calculateTorsoRotation(angle, -150, -30, 30, 150, 180, 0, -1, 1, 1, 1, false);
                calculateLegRotation(angle, -150, -30, 30, 150, -1, 1, 1, 1, false);
            }

            if (Utilities.gravityDir.Equals(GravityDirection.West))
            {
                calculateTorsoRotation(angle, -150, -30, 30, 150, 180, 0, -1, -1, 1, -1, false);
                calculateLegRotation(angle, -150, -30, 30, 150, 1, 1, -1, 1, false);
            }
            //Debug.Log(angle);
    }

    void rotateCharacterToCursor2(float angle)
    {

        if (Utilities.gravityDir.Equals(GravityDirection.South))
        {
            calculateTorsoRotation(angle, 90, -90, -90, 90, 180, 0, -1, 1, 1, 1, true);
            calculateLegRotation(angle, 90, -90, 90, -90, -1, 1, 1, 1, true);
        }

        if (Utilities.gravityDir.Equals(GravityDirection.North))
        {
            calculateTorsoRotation(angle, 90, -90, -90, 90, 180, 0, -1, -1, 1, -1, true);
            calculateLegRotation(angle, 90, -90, 90, -90, 1, 1, -1, 1, true);
        }

        if (Utilities.gravityDir.Equals(GravityDirection.East))
        {
            calculateTorsoRotation(angle, -180, 0, 0, 180, 180, 0, -1, 1, 1, 1, false);
            calculateLegRotation(angle, -180, 0, 0, 180, -1, 1, 1, 1, false);
        }

        if (Utilities.gravityDir.Equals(GravityDirection.West))
        {
            calculateTorsoRotation(angle, -180, 0, 0, 180, 180, 0, -1, -1, 1, -1, false);
            calculateLegRotation(angle, -180, 0, 0, 180, 1, 1, -1, 1, false);
        }
        //Debug.Log(angle);
    }

    void calculateTorsoRotation(float angle, float floor1, float floor2, float floor3, float floor4, float offset1, float offset2, int x1, int y1, int x2, int y2, bool vertical)
    {
        if (torso)
        {
            if (vertical)
            {
                if (angle >= floor1 || angle <= floor2)
                {
                    transform.localScale = new Vector2(x1, y1);
                    transform.rotation = Quaternion.Euler(new Vector3(0, 0, angle + offset1));
                }
                else if (angle > floor3 && angle < floor4)
                {
                    transform.localScale = new Vector2(x2, y2);
                    transform.rotation = Quaternion.Euler(new Vector3(0, 0, angle + offset2));
                }
            }
            else
            {
                if (angle >= floor1 && angle <= floor2)
                {
                    transform.localScale = new Vector2(x1, y1);
                    transform.rotation = Quaternion.Euler(new Vector3(0, 0, angle + offset1));
                }
                else if (angle >= floor3 && angle <= floor4)
                {
                    transform.localScale = new Vector2(x2, y2);
                    transform.rotation = Quaternion.Euler(new Vector3(0, 0, angle + offset2));
                }
            }
        }
    }

    void calculateLegRotation(float angle, float floor1, float floor2, float floor3, float floor4, int x1, int y1, int x2, int y2, bool vertical)
    {
        if (!torso)
        {
            if (vertical)
            {
                if (angle >= floor1 || angle <= floor2)
                {
                    transform.localScale = new Vector2(x1, y1);
                }
                else if (angle < floor3 && angle > floor4)
                {
                    transform.localScale = new Vector2(x2, y2);
                }
            }
            else
            {
                if (angle >= floor1 && angle <= floor2)
                {
                    transform.localScale = new Vector2(x1, y1);
                }
                else if (angle > floor3 && angle < floor4)
                {
                    transform.localScale = new Vector2(x2, y2);
                }
            }
        }
    }
}
