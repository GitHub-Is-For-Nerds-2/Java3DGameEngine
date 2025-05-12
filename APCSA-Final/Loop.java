public class Loop implements Runnable           //Runnable enables threading
{
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    
    private Thread thread;                      //Object to have multiple things
                                                //running at the same time
    private boolean isRunning = false;          //Control System Loop
    
    private Render render;                      //Access the render pipeline
    
    public void start()
    {
        if(isRunning) { return; }               //Check if the game is already running.
                                                //If it is, exit start so code doesn't 
                                                //rerun, else start the game.
                                                
        Window window = new Window();           //Create window
        window.window(WIDTH, HEIGHT);
                                                
        isRunning = true;                       //Set isRunning true for previouis check
        
        thread =  new Thread(this);             //Create thread
        thread.start();                         //Start threading
        
        System.out.println("Debug: Running...");
    }
    
    public void run()                           //Override of Runnable implimentation
    {
        while(isRunning)
        {
            tick();
        }
    }
    
    private void tick()
    {
        
    }
    
    private void render()
    {
        render = new Render(WIDTH, HEIGHT);
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
