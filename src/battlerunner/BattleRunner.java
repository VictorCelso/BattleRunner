/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battlerunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
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

/**
 *
 * @author v.tassinari
 */
public class BattleRunner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //-DROBOTPATH=C:/Users/v.tassinari/Documents/NetBeansProjects/War/dist/War.jar
        try {
            //Cria um compilador em tempo de execução
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays
                    .asList(new File("C:/Users/v.tassinari/Documents/NetBeansProjects/War/build/classes/")));
            Arrays.asList(
                            new File("C:/Users/v.tassinari/Documents/NetBeansProjects/War/src/Tank/").listFiles()).forEach(file->{
                                System.out.println(file.getName());
                            });
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
            System.out.println(compiler.getTask(null, fileManager, diagnostics, null, null, fileManager
                    .getJavaFileObjects(
                            new File("C:/Users/v.tassinari/Documents/NetBeansProjects/War/src/Tank/").listFiles())).call());
            
            /*
            //O compilador procura e compila o arquivo .java no caminho especificado.
            System.out.println(compiler.run(null, null, null, "C:/Users/v.tassinari/Documents/NetBeansProjects/War/src/Tank/SmartShooter.java"));

            //Procura o arquivo antigo já compilado e deleta o mesmo
            File file = new File("C:/Users/v.tassinari/Documents/NetBeansProjects/War/build/classes/Tank/SmartShooter.class");
            file.delete();
            //Move o arquivo compilado para o caminho em que o antigo arquivo estava.
            Files.move(Paths.get("C:/Users/v.tassinari/Documents/NetBeansProjects/War/src/Tank/SmartShooter.class"),
                Paths.get("C:/Users/v.tassinari/Documents/NetBeansProjects/War/build/classes/Tank/SmartShooter.class"));
            */
        } catch (IOException ex) {
            Logger.getLogger(BattleRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                    System.out.println("Robo: " + result.getTeamLeaderName() + " score: " + result.getScore());
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
        RobotSpecification[] robots = engine.getLocalRepository("sample.Tracker,Tank.SmartShooter*,Tank.batman*");
        BattleSpecification battle = new BattleSpecification(5, field, robots);
        //Exibição da batalha.
        engine.setVisible(true);
        //Quantidade de vezes que a batalha vai ser executada
        for (int x = 0; x < 3; x++) {
            engine.runBattle(battle, true);
        }
        System.exit(0);
    }

}
