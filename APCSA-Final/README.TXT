PROJECT TITLE:              3D Java Game Engine
PURPOSE OF PROJECT:         Computer Science A final project
VERSION or DATE:            6.2.25
HOW TO START THIS PROJECT:  Entry point is Main.main();
AUTHORS:                    Me            
USER INSTRUCTIONS:          Really bad game engine made in Java. Works but really messy and not user friendly.
                            Heavily based on: https://www.youtube.com/playlist?list=PLsRmsZm0xMNogPyRn6gNWq4OM5j22FkAU
                            
ENGINE ARCHITECTURE:
This engine uses a package based system where everything opperates (as much as possible) independently from eachother.
Currently, there is only a RenderEngine, InputSystem, and Math package which are explained below.

Main:
Main - main entry poin of the app, creates all the objects from the packages 

InputSystem:
InputSystem - Listens for keyboard and sets booleans in an array linked to each key as true or false based on if they're
              pressed.
              
Math:
Vector - vector and related opperations

RenderEngine (where things get messy):
Window - main entry point for this package. Creates window and instantiates screen object. Intakes screen dimensions for 
         further use and input system to added to screen and then sent to camera
Screen - where the screen is created, drawn and updated
Camera - Camera movement and related math
PolygonObject - Where primitive (2D) polygons were created (should be removed in favor of DPolygon)
DPolygon - 3D polygons object
Calculator - Where the 3D word space and 2D camera space are calculated and sent to screen for rendering
