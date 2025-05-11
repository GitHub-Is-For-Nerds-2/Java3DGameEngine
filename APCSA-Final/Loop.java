
public class Loop implements Runnable         //Runnable enables threading
{
    private static Thread thread;               //Object to have multiple things
                                                //running at the same time
    private static boolean isRunning = false;   //Control System Loop
    
    public void start()
    {
        if(isRunning) { return; }               //Check if the game is already running.
                                                //If it is, exit start so code doesn't 
                                                //rerun, else start the game.
                                                
        isRunning = true;                       //Set isRunning true for previouis check
        
        thread =  new Thread(this);             //Create thread
        thread.start();                         //Start threading
        
        System.out.println("Debug: Running...");
    }
    
    public void run()                           //Override of Runnable implimentation
    {
        while(isRunning)
        {
                        
        }
    }
    
    private void stop()
    {
        if(!isRunning){ return; };
        
        isRunning = false;
        
        //This is a try catch, it basically is an error check so if the method it is trying
        //to run fails, it exicutes the code in the catch so the app doesn't crash.
        try
        {
            thread.join();                      //Join threads so they can end 
        }
        catch(Exception exception)              //Exception is javas error code library
        {
            exception.printStackTrace();        //printStackTrace prints the stack location
                                                //of the error
            System.exit(0);                     //Close the app
        }
    }
}
