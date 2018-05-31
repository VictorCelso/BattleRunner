/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battlerunner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import robocode.BattleResults;
import robocode.control.*;
import robocode.control.events.*;
import robotevolver.Bot;
import robotevolver.Factory;

/**
 *
 * @author Victor Tassinari
 */
public class BattleRunner {

    static String figthers = "sample.Tracker,rb.Sniper*,rb.Dodger*,rb2.Saitama*";
    static Map<String, Integer> nomes = new HashMap<>();
    static List<Bot> bots = null;
    static Factory factory = Factory.getInstance();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //-DROBOTPATH=C:/Users/Victor Tassinari/Documents/NetBeansProjects/War/dist/War.jar

//            String run, onScanned, onHitByBullet;
//            run = "@Override\n"
//                    + "    public void run() {\n"
//                    + "        setAdjustGunForRobotTurn(true);\n"
//                    + "        while (true) {\n"
//                    + "            movDir *=-1;\n"
//                    + "            turnGunRight(lastPos);\n"
//                    + "            turnSearching++;\n"
//                    + "            if (turnSearching > 2) {\n"
//                    + "                lastPos = -10;\n"
//                    + "            } else if (turnSearching > 5) {\n"
//                    + "                lastPos = 10;\n"
//                    + "            } else if (turnSearching > 8) {\n"
//                    + "                lastPos= 360;\n"
//                    + "            }\n"
//                    + "        }\n"
//                    + "    }";
//            onScanned = "@Override\n"
//                    + "    public void onScannedRobot(ScannedRobotEvent e) {\n"
//                    + "        turnSearching = 0;\n"
//                    + "        lastPos = normalRelativeAngleDegrees(e.getBearing()+(getHeading() - getRadarHeading()));\n"
//                    + "        setTurnGunRight(lastPos);\n"
//                    + "        setTurnRight(e.getBearing()+90-30*movDir);\n"
//                    + "        System.out.println(e.getDistance());\n"
//                    + "        if (e.getDistance() < 60) {\n"
//                    + "            back(60);\n"
//                    + "        } else {\n"
//                    + "            ahead(e.getDistance() - 140);\n"
//                    + "        }\n"
//                    + "        \n"
//                    + "        setTurnGunRight(lastPos);\n"
//                    + "        if (getGunTurnRemaining() < 10) {\n"
//                    + "            if (Math.abs(e.getDistance()) < 150) {\n"
//                    + "                fire(3);\n"
//                    + "            } else {\n"
//                    + "                fire(1);\n"
//                    + "            }\n"
//                    + "        }\n"
//                    + "        scan();\n"
//                    + "        lastPos = normalRelativeAngleDegrees(e.getBearing()+(getHeading() - getRadarHeading()));\n"
//                    + "        setTurnGunRight(lastPos);\n"
//                    + "        fire(1);\n"
//                    + "     turnSearching=0;\n"
//                    + "   }";
//            onHitByBullet = "@Override\n"
//                    + "    public void onHitByBullet(HitByBulletEvent e) {\n"
//                    + "        setTurnRight(e.getBearing()+90-30*e.getHeading()*movDir);\n"
//                    + "        back(70*movDir);\n"
//                    + "        lastPos=normalRelativeAngleDegrees(e.getBullet().getHeading()-e.getBearing());\n"
//                    + "    }";
//            //Cria um compilador em tempo de execução
//            FileWriter writer = new FileWriter(
//                    new File("C:/Users/Victor Tassinari/Documents/NetBeansProjects/Rb/src/rb/evolvingBot.java"));
//            writer.write("package rb;\n"
//                    + "import robocode.AdvancedRobot;\n"
//                    + "import robocode.HitByBulletEvent;\n"
//                    + "import robocode.ScannedRobotEvent;\n"
//                    + "import static robocode.util.Utils.normalRelativeAngleDegrees;\n"
//                    + "/*\n*@author Victor Tassinari\n*/\n"
//                    + "public class evolvingBot extends AdvancedRobot{\n"
//                    + "    int movDir = 1;\n"
//                    + "    double lastPos = 10;\n"
//                    + "    int turnSearching = 0;\n"
//                    + run
//                    + onScanned
//                    + onHitByBullet
//                    + "}");
//            writer.close();
        /*
         //O compilador procura e compila o arquivo .java no caminho especificado.
         System.out.println(compiler.run(null, null, null, "C:/Users/Victor Tassinari/Documents/NetBeansProjects/War/src/Tank/SmartShooter.java"));

         //Procura o arquivo antigo já compilado e deleta o mesmo
         File file = new File("C:/Users/Victor Tassinari/Documents/NetBeansProjects/War/build/classes/Tank/SmartShooter.class");
         file.delete();
         //Move o arquivo compilado para o caminho em que o antigo arquivo estava.
         Files.move(Paths.get("C:/Users/Victor Tassinari/Documents/NetBeansProjects/War/src/Tank/SmartShooter.class"),
         Paths.get("C:/Users/Victor Tassinari/Documents/NetBeansProjects/War/build/classes/Tank/SmartShooter.class"));
         */
        //Para deixar o log mais limpo.
        RobocodeEngine.setLogMessagesEnabled(false);
//        System.out.println(SmartShooter.class.getClassLoader().getResource("Tank"));
//        compiler.compile(SmartShooter.class.get, fileName);

        //Carrega o robocode.
        RobocodeEngine engine = new RobocodeEngine(new java.io.File("C:/Robocode"));

        //Cria um monitor de batalha, utilizado aqui apenas para pegar os resultados das batalhas, e visualizar possiveis erros.
        engine.addBattleListener(new BattleAdaptor() {
            @Override
            public void onBattleCompleted(BattleCompletedEvent event) {
                super.onBattleCompleted(event); //To change body of generated methods, choose Tools | Templates.
                List<BattleResults> results = Arrays.asList(event.getSortedResults());

                results.forEach(result -> {
                    nomes.put(result.getTeamLeaderName(), result.getScore());
//                    System.out.println("Robo: " + result.getTeamLeaderName() + " score: " + result.getScore());
                });
            }

            @Override
            public void onBattleError(BattleErrorEvent event) {
                super.onBattleError(event); //To change body of generated methods, choose Tools | Templates.
                System.out.println(event.getError());
            }

        });
        //Tamanho do campo de batalha.
        BattlefieldSpecification field = new BattlefieldSpecification(800, 800);
        //Definição dos robos que irão para batalha.

        bots = factory.robotsBuilder();
        buildBots();
        for (int y = 0; y < 51; y++) {
            factory.setShipmentFitness(0);
            figthers = "sample.Tracker,rb.Sniper*,rb.Dodger*,rb2.Saitama*";
            bots.forEach(bot -> {
                figthers += ",rb." + bot.getName() + "*";
            });
            RobotSpecification[] robots = engine.getLocalRepository(figthers);
            BattleSpecification battle = new BattleSpecification(5, field, robots);
            //Exibição da batalha.
            engine.setVisible(true);
            //Quantidade de vezes que a batalha vai ser executada
            for (int x = 0; x < 3; x++) {
                engine.runBattle(battle, true);
                bots.forEach(b -> {
                    b.setFitness(b.getFitness() + nomes.get("rb." + b.getName() + "*"));
                    System.out.println(b.getFitness());
                });

            }
            bots.forEach(b -> {
                b.setFitness(b.getFitness() / 3);
                factory.setShipmentFitness(factory.getShipmentFitness() + b.getFitness());
//                System.out.println(b.getFitness());
                b.getRobotFile().delete();
            });
            factory.setShipmentFitness(factory.getShipmentFitness() + (factory.getShipmentFitness() / bots.size()));
            bots = factory.robotsCrossing(bots);
            buildBots();
            System.out.println("Generation: " + y + " Fitness: " + factory.getShipmentFitness() + " LastOne: " + factory.getLastFitness());
        }
        System.exit(0);
    }

    private static void buildBots() {

        try {

            bots.forEach((Bot bot) -> {
                bot.setPath("C:/Users/Victor Tassinari/Documents/NetBeansProjects/Rb/src/rb/" + bot.getName() + ".java");
                bot.setRobotFile(new File(bot.getPath()));
                try (FileWriter fwriter = new FileWriter(
                        bot.getRobotFile())) {
                    fwriter.write(bot.getContent());
                    fwriter.close();
                } catch (IOException ex) {
                    Logger.getLogger(BattleRunner.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays
                    .asList(new File("C:/Users/Victor Tassinari/Documents/NetBeansProjects/Rb/build/classes/")));
            Arrays.asList(
                    new File("C:/Users/Victor Tassinari/Documents/NetBeansProjects/Rb/src/rb/").listFiles()).forEach(file -> {
                        System.out.println(file.getName());
                    });

            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
            System.out.println(compiler.getTask(null, fileManager, diagnostics, null, null, fileManager
                    .getJavaFileObjects(
                            new File("C:/Users/Victor Tassinari/Documents/NetBeansProjects/Rb/src/rb/").listFiles())).call());

        } catch (IOException ex) {
            Logger.getLogger(BattleRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
