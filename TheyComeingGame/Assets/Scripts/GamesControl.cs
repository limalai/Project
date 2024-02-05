using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class GamesControl : MonoBehaviour
{

    private float a, b;
    private float Gotcha;
    private float Catch;
    private float GotchaChoice;
    private float CatchChoice;//keep the right answer
    public GameObject[] choiceBtn; //number of choice
    public string GotchatagBtn;
    public string CatchtagBtn;//tell what the right/wrong choice
    public static GamesControl instance;

    //public int loadNextLevel;

    private void Awake()
    {
        MakeInstance();
    }
    void MakeInstance()
    {
        if (instance == null)
        {
            instance = this;
        }
    }

    // Start is called before the first frame update
    void Start()
    {
        AdditionMethode();
        //loadNextLevel = SceneManager.GetActiveScene().buildIndex + 1;

    }

    // Update is called once per frame
    void Update()
    {
        //แปลงเป็น string
        GotchatagBtn = GotchaChoice.ToString();
        CatchtagBtn = CatchChoice.ToString();
    }

    public void NextPage()
    {
        //SceneManager.LoadScene("Level " + loadNextLevel);
    }


    //สร้างฟังก์ชันในการสุ่มตัวเลขและนำตัวเลขมาบวกกัน
    public void AdditionMethode()
    {
        a = Random.Range(0, 2); //0-1
        b = Random.Range(0, 2);
        Gotcha = a + b;
        Catch = a + b;
        if (Catch == Gotcha)
        {
            Catch = a + b;
        }

        Debug.Log(Gotcha);
        Debug.Log(Catch);

        //สุ่มตำแหน่งที่จะใส่คำตอบที่ถูกต้องลงไป
        GotchaChoice = Random.Range(0, choiceBtn.Length);
        CatchChoice = Random.Range(0, choiceBtn.Length);
        if (CatchChoice == GotchaChoice)
        {
            CatchChoice = Random.Range(0, choiceBtn.Length);
        }
    }

    //popup gotcha

    public GameObject onClickPopup;

    public void Click()
    {
        onClickPopup.SetActive(true);
    }
    public void ClosePopups()
    {
        onClickPopup.SetActive(false);
    }

// popup catch
    public GameObject CatchPopup;
    public void CatchClick()
    {
        CatchPopup.SetActive(true);
    }
    public void CatchClosePopup()
    {
        CatchPopup.SetActive(false);
    }

    //popup setting



}
