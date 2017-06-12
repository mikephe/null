using UnityEngine;
using System.Collections;


public class Player : Entity {
    private WeaponHandler wh;
    private Rigidbody2D rb;
    public LayerMask lm;
    public WeaponState state = WeaponState.Standard;
    private int weaponIndex = 0;
    private int jumpCount = 0;
    public Material gravMat;

    private Vector3 rightDir;
    private float rightAngle = 0;
    // Use this for initialization
    void Start()
    {
        rb = GetComponent<Rigidbody2D>();
        wh = GetComponent<WeaponHandler>();
        health = Utilities.currentHealth;
        state = Utilities.playerState;
        weaponIndex = Utilities.playerWeaponIndex;
    }

    public int getWeaponIndex()
    {
        return weaponIndex;
    }

    // Update is called once per frame
    void Update()
    {
        EntityStart();
        EntityUpdate();
        CheckInputs();
        GroundCheck();
        //Debug.Log(isGrounded);
        deathCheck();

        if (Utilities.rightJoyAngle() != 0)
        {
            rightAngle = Utilities.rightJoyAngle();
            rightDir = Utilities.rightDir();
        }
        else
        {
            rightDir = new Vector3(1, 0, 1);
        }
    }

    override protected void playerMaterial()
    {
        /*
		 * call to update after getting hit so that we go back to default material,
		 */
        if ((hitTime + 0.1f < Time.time))
        {
            foreach (SpriteRenderer s in gameObject.GetComponentsInChildren<SpriteRenderer>())
            {
                s.material = defMat;
            }
        }

        if (hitTime + 0.1f >= Time.time)
        {
            //Debug.Log("HIT");
            foreach (SpriteRenderer s in gameObject.GetComponentsInChildren<SpriteRenderer>())
            {
                s.material = hitMat;
            }
        }
    }

    void GroundCheck()
    {
        RaycastHit2D hit = Physics2D.Raycast(gameObject.transform.position, new Vector2(0, -1), 0.5f, lm);
        if (Utilities.gravityDir.Equals(GravityDirection.North))
            hit = Physics2D.Raycast(gameObject.transform.position, new Vector2(0, 1), 0.5f, lm);
        if (Utilities.gravityDir.Equals(GravityDirection.East))
            hit = Physics2D.Raycast(gameObject.transform.position, new Vector2(1, 0), 0.5f, lm);
        if (Utilities.gravityDir.Equals(GravityDirection.West))
            hit = Physics2D.Raycast(gameObject.transform.position, new Vector2(-1, 0), 0.5f, lm);

        if (hit.collider)
        {
            isGrounded = true;
            jumpCount = 0;
        }
        else {
            isGrounded = false;
        }
    }

    /**
    * Used for all player button inputs...I guess...
*/
    void CheckInputs()
    {
        movementCheck();
        weaponCheck();
        gravityCheck();
    }

    private void weaponCheck()
    {
        bool time = (wh.pm[weaponIndex].hitTime + wh.pm[weaponIndex].shotCooldown < Time.time);

            if (Input.GetAxis("RT") != 0 && time)
            {
                //Debug.Log(rightAngle);
                wh.createProjectile(weaponIndex, rightAngle, rightDir);
                if (wh.pm[weaponIndex].sfx != null)
                {
                    audio.PlayOneShot(wh.pm[weaponIndex].sfx, 1);
                }
                //pM.hitTime = Time.time;
            }


            if (Input.GetMouseButton(0) && time)
            {
                //Debug.Log(Utilities.calculateDir(transform));
                wh.createProjectile(weaponIndex, Utilities.calculateMouseAngle(transform), Utilities.calculateDir(transform));
                if (wh.pm[weaponIndex].sfx != null)
                {
                    audio.PlayOneShot(wh.pm[weaponIndex].sfx, 1);
                }
                //pM.hitTime = Time.time;
            }


        if (Input.GetMouseButtonDown(1) || Input.GetButtonDown("LB") || Input.GetButtonDown("RB"))
        {
            if (Input.GetButtonDown("LB"))
                weaponIndex--;
            else if (Input.GetButtonDown("RB"))
                weaponIndex++;
            else
                weaponIndex++;

            if (weaponIndex < 0)
                weaponIndex = 3;
            foreach (ProjectileMovement p in wh.pm)
            {
                p.GetComponent<SpriteRenderer>().enabled = false;
            }
            if (weaponIndex >= wh.pm.Count || weaponIndex == 0)
            {
                state = WeaponState.Standard;
                weaponIndex = 0;
                wh.pm[weaponIndex].GetComponent<SpriteRenderer>().enabled = true;
            }
            if (weaponIndex == 1)
            {
                state = WeaponState.Shotgun;
                wh.pm[weaponIndex].GetComponent<SpriteRenderer>().enabled = true;
            }
            if (weaponIndex == 2)
            {
                state = WeaponState.Railgun;
                wh.pm[weaponIndex].GetComponent<SpriteRenderer>().enabled = true;
            }
            if (weaponIndex == 3)
            {
                state = WeaponState.Launcher;
                wh.pm[weaponIndex].GetComponent<SpriteRenderer>().enabled = true;
            }

        }

    }

    private void gravityCheck()
    {
        if (Input.GetKey(KeyCode.LeftShift) || Input.GetAxis("gravity") != 0)
        {
            if (gravMat != null)
            {
                foreach (SpriteRenderer s in gameObject.GetComponentsInChildren<SpriteRenderer>())
                {
                    s.material = gravMat;
                }

            }
            if (Input.GetAxis("Horizontal") > 0)
            {
                Utilities.gravityDir = GravityDirection.East;
            }
            if (Input.GetAxis("Horizontal") < 0)
            {
                Utilities.gravityDir = GravityDirection.West;
            }
            if (Input.GetAxis("Vertical") > 0)
            {
                Utilities.gravityDir = GravityDirection.North;
            }
            if (Input.GetAxis("Vertical") < 0)
            {
                Utilities.gravityDir = GravityDirection.South;
            }
        }
        if (Input.GetMouseButtonUp(2))
        {
            foreach (SpriteRenderer s in gameObject.GetComponentsInChildren<SpriteRenderer>())
            {
                s.material = defMat;
            }
        }

    }

    private void movementCheck()
    {
        if (Utilities.gravityDir.Equals(GravityDirection.North))
        {
            transform.rotation = Quaternion.Euler(new Vector3(0, 0, 180));
            rb.velocity = new Vector2(Input.GetAxis("Horizontal") * walkVel, rb.velocity.y);
        }

        if (Utilities.gravityDir.Equals(GravityDirection.South))
        {
            transform.rotation = Quaternion.Euler(new Vector3(0, 0, 0));
            rb.velocity = new Vector2(Input.GetAxis("Horizontal") * walkVel, rb.velocity.y);
        }

        if (Utilities.gravityDir.Equals(GravityDirection.East))
        {
            transform.rotation = Quaternion.Euler(new Vector3(0, 0, 90));
            rb.velocity = new Vector2(rb.velocity.x, Input.GetAxis("Vertical") * walkVel);
        }

        if (Utilities.gravityDir.Equals(GravityDirection.West))
        {
            transform.rotation = Quaternion.Euler(new Vector3(0, 0, -90));
            rb.velocity = new Vector2(rb.velocity.x, Input.GetAxis("Vertical") * walkVel);
        }

        if (Input.GetButtonDown("Jump"))
        {
            if (jumpCount < 1)
            {
                if (Utilities.gravityDir.Equals(GravityDirection.South)) 
                    rb.velocity = new Vector2(rb.velocity.x, jumpVel);
                if (Utilities.gravityDir.Equals(GravityDirection.North))
                    rb.velocity = new Vector2(rb.velocity.x, -jumpVel);
                if (Utilities.gravityDir.Equals(GravityDirection.East))
                    rb.velocity = new Vector2(-jumpVel, rb.velocity.y);
                if (Utilities.gravityDir.Equals(GravityDirection.West))
                    rb.velocity = new Vector2(jumpVel, rb.velocity.y);

                jumpCount++;
                //Debug.Log("Jump Count: " + jumpCount);
            }
        }

        if (r.velocity.x >= maxWalkVel)
        {
            r.velocity = new Vector2(maxWalkVel, r.velocity.y);
        }

        if (r.velocity.y >= maxWalkVel)
        {
            r.velocity = new Vector2(r.velocity.x, maxWalkVel);
        }
    }


}
