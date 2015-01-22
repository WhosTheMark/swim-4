package controller;

import jmsmainapp.JMSManager;

public class SWIM {

	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("ERROR - Bad arguments");
			System.out.println("you need to provide the name of the xml file you want to use ase scenario");
		} else {
			SWIMController swimController
				= new SWIMController(JMSManager.getInstance());
			swimController.runScenario(args[0]);
		}
	}
}
