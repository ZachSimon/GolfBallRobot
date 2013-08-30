This program models a driving range and a theoretical robot that picks up balls on this driving range to try to determine what is the best path for the robot to take if it wants to maximize ball collection. Trying out your own path algorithm is fun and easy!

Run Simulation to run the simulation. By default, it is set to run with a gui for fewer timesteps and simulations than ideal for analysis. To run simulations for analysis, change GUI_SWITCH to false, TIMESTEPS to 5760 and SIMS_TO_RUN to 500. view out.txt to view the results of simulations that are run.

This project uses the apache commons math library, available here: http://commons.apache.org/proper/commons-math/

To try your own algorithm, implement the Roomba interface and add it to the static array "roombas" in Simulation.java (feel free to remove other roombas too). The only method in the Roomba interface is getMove(Simulation sim) which asks your roomba for a move. The Simulation has many public methods which you can query (such as the current timestep or current location) to determine your moves, but feel free to store your own data as well.

To view our analysis, read Analysis.pdf, to read about how the driving range is modeled, view doc/about.txt