using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;


public class CheckBtn : MonoBehaviour
{
    private AudioSource au;
    public AudioClip[] sound;
    public int loadNextLevel;
    void Start()
    {
        au = GetComponent<AudioSource>();
        loadNextLevel = SceneManager.GetActiveScene().buildIndex + 2;
        
    }

    public void CheckTagBtn()
    {
        if (gameObject.CompareTag(GamesControl.instance.GotchatagBtn))
        {      
            //ไปด่านต่อไปเลย
            if (loadNextLevel > PlayerPrefs.GetInt("CurrentLevel"))
            {
                PlayerPrefs.SetInt("CurrentLevel", loadNextLevel);
            }           
            GamesControl.instance.Click();
            au.PlayOneShot(sound[0]);
            GamesControl.instance.AdditionMethode();
        }
        else if (gameObject.CompareTag(GamesControl.instance.CatchtagBtn))
        {   
            GamesControl.instance.CatchClick();
            au.PlayOneShot(sound[1]);
            GamesControl.instance.AdditionMethode();
        }
    }

    

}
