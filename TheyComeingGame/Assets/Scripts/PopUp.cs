using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PopUp : MonoBehaviour
{

    public GameObject volumePage;

    public void Open()
    {
        volumePage.SetActive(true);
    }

    
    public void Close()
    {
        volumePage.SetActive(false);
    }


}
