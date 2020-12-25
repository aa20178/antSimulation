package ch.epfl.moocprog.tests;

import ch.epfl.moocprog.app.ApplicationInitializer;
import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.config.ImmutableConfigManager;
import java.io.File;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.WORLD_HEIGHT;
import static ch.epfl.moocprog.config.Config.WORLD_WIDTH;

import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Vec2d;
import ch.epfl.moocprog.ToricPosition;
import ch.epfl.moocprog.Environment;
import ch.epfl.moocprog.Food;
import ch.epfl.moocprog.Positionable;
import ch.epfl.moocprog.Termite;
public class Main {

    public static void main(String[] args) {
        ApplicationInitializer.initializeApplication(
            new ImmutableConfigManager(
                new File("res/app.cfg")
            )
        );
        final int width  = getConfig().getInt(WORLD_WIDTH);
        final int height = getConfig().getInt(WORLD_HEIGHT);

        ToricPosition tp1 = new ToricPosition();
        ToricPosition tp2 = new ToricPosition(1.2, 2.3);
        ToricPosition tp3 = new ToricPosition(new Vec2d(4.5, 6.7));

        
        Food f1 = new Food(tp2, 4.7);
        Food f2 = new Food(tp3, 6.7);

        System.out.println();
        
        System.out.println("Some tests for Food");
        System.out.println("Display : ");
        System.out.println(f1);
        System.out.println("Initial : " + f1.getQuantity()
        + ", taken : "
        + f1.takeQuantity(5.0)
        + ", left : " + f1.getQuantity());
        System.out.println("Initial : " + f2.getQuantity()
        + ", taken : "
        + f2.takeQuantity(2.0)
        + ", left : "
        + f2.getQuantity());
        final Time foodGenDelta = getConfig().getTime(Config.FOOD_GENERATOR_DELAY);
        Environment env = new Environment();
        env.addFood(f1);
        env.addFood(f2);
        System.out.println();
        System.out.println("Some tests for Environment");
        System.out.println("Inital food quantities : " + env.getFoodQuantities());
        env.update(foodGenDelta);
        System.out.println("After update : " + env.getFoodQuantities());

        
        
        System.out.println();
        System.out.println ("A termite before update :");
        Termite t1 = new Termite(new ToricPosition(20, 30));
        System.out.println(t1);
        env.addAnimal(t1);
        env.update(Time.fromSeconds(1.));
        System.out.println("The same termite after one update :");
        System.out.println(t1);

    }
}
