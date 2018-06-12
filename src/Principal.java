import javafx.scene.paint.Color;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;

//Jose Enrique Reyes Huerta

class Cronometro extends JPanel implements Runnable,Serializable{


    private javax.swing.JButton btnPause;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStop;
    private javax.swing.JButton btnreanu;
    private javax.swing.JButton btnRegist;
    private javax.swing.JLabel horas;
    private javax.swing.JLabel minutos;
    private javax.swing.JLabel segundos;
    private javax.swing.JLabel cero1;
    private javax.swing.JLabel cero2;

    private int hora;
    private int min;
    private int seg;
    private  boolean iniciar;


    private boolean banderaLimiteSegundo;
    private boolean banderaLimiteMinutos;
    private int limite;
    private Thread hilo1;



    public Cronometro (){

        this.initComponents();
        this.hilo1 = null;
        this.hora = 0;
        this.min = 0;
        this.seg = 0;


        this.iniciar = false;
        horas.setText(String.valueOf(this.hora));
        minutos.setText(String.valueOf(this.min));
        segundos.setText(String.valueOf(this.seg));


    }

    public int getHoras(){

        return this.hora;

    }
    public int getMinutos(){

        return this.min;

    }
    public int getSegundos(){

        return this.seg;

    }

    public void setHoras(int hora){
        this.hora = hora;
    }
    public void setMinutos(int minutos){
        this.min = min;
    }
    public void setSegundos(int segundos) {
        this.seg = seg;
    }
    public void setLimiteSegundos(int limite){
        this.banderaLimiteSegundo = true;
        this.limite = limite;
    }
    public void setLimiteMinutos(int limite){
        this.banderaLimiteMinutos = true;
        this.limite = limite;
    }



    private void initComponents() {
        //Reloj relojChetumal = new Reloj();
        this.setLayout(null);
        this.setSize(400, 200);


        horas = new javax.swing.JLabel();
        horas.setBounds(50, 50, 50, 50);

        this.add(horas);


        cero1 = new javax.swing.JLabel();
        cero1.setBounds(110, 50, 50, 50);
        cero1.setText(":");
        this.add(cero1);

        minutos = new javax.swing.JLabel();
        minutos.setBounds(170, 50, 50, 50);

        this.add(minutos);

        cero2 = new javax.swing.JLabel();
        cero2.setBounds(230, 50, 50, 50);
        cero2.setText(":");
        this.add(cero2);

        segundos = new javax.swing.JLabel();
        segundos.setBounds(290, 50, 50, 50);

        this.add(segundos);


        btnStart = new javax.swing.JButton();
        btnStart.setBounds(20, 150, 80, 30);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciar();
            }
        });
        this.add(btnStart);

        btnPause = new javax.swing.JButton();
        btnPause.setBounds(110, 150, 80, 30);
        btnPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pausar();
            }
        });
        this.add(btnPause);

        btnStop = new javax.swing.JButton();
        btnStop.setBounds(200, 150, 90, 30);
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detener();
            }
        });
        this.add(btnStop);

        btnreanu = new javax.swing.JButton();
        btnreanu.setBounds(300, 150, 90, 30);
        btnreanu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reanudar();
            }
        });
        this.add(btnreanu);


        btnStart.setText("Iniciar");
        btnPause.setText("Pausar");
        btnStop.setText("Detener");
        btnreanu.setText("Reanudar");

    }

    public void iniciar(){
        if(this.hilo1 == null){
            this.hora = 0;
            this.min = 0;
            this.seg = 0;
            this.iniciar = true;
            this.hilo1 = new Thread(this);
            this.hilo1.start();
        }

    }

    public void pausar(){
        if(this.hilo1 != null){
            this.iniciar = false;
        }
    }

    public void reanudar(){
        if(this.hilo1 != null){
            this.iniciar = true;
            this.hilo1 = new Thread(this);
            this.hilo1.start();
        }
    }

    public void detener(){
        if(this.hilo1 != null){
            System.out.println("el segundo fue: "+this.seg);
            System.out.println("el minuto fue: "+this.min);
            System.out.println("la hora fue: "+this.hora);
            this.iniciar = false;
            this.hilo1 = null;
        }
    }
    public void run(){
        try {
            String segundo = "0";
            String minuto = "0";
            String hora = "0";
            int valor = 5;

            while (this.iniciar) {
                this.seg += 1;

                if(this.banderaLimiteSegundo){
                    if(this.seg >= this.limite){
                        JOptionPane.showMessageDialog(null,"segundos transcurrridos : "+this.limite);
                        this.banderaLimiteSegundo = false;
                        this.limite = 0;

                    }
                } else if(this.banderaLimiteMinutos){
                    if(this.min >= this.limite){
                        JOptionPane.showMessageDialog(null,"minutos transcurridos: "+this.limite);
                        this.banderaLimiteMinutos = false;
                        this.limite = 0;
                    }
                }


                if(this.seg >= 59){
                    this.seg = 0;
                    this.min += 1;
                }
                if(this.min >= 59) {
                    this.min = 0;
                    this.hora += 1;
                }

                segundo = "0" + this.seg;
                minuto = "0" + this.min;
                hora = "0" + this.hora;

                if(segundo.length() >= 3){
                    segundo = String.valueOf(this.seg);
                }
                if(minuto.length() >= 3){
                    minuto = String.valueOf(this.min);
                }
                if(hora.length() >= 3){
                    hora = String.valueOf(this.hora);
                }

                this.segundos.setText(segundo);
                this.minutos.setText(minuto);
                this.horas.setText(hora);

                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}

class  Ventana extends JFrame{
    public Ventana (){
this.initComponents();


    }
    private void initComponents() {
     this.setSize(600,440);
     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     this.setLayout(null);

     Cronometro cron = new Cronometro();
     cron.setLimiteMinutos(10);

     cron.setBackground(new java.awt.Color(255,0,25));
     cron.setLocation(0,0);


     this.add(cron);


     Cronometro cron1 = new Cronometro();
     cron1.setBackground(new java.awt.Color(0,0,255));
     cron1.setLocation(0,cron.getHeight());
     this.add(cron1);

     JTextField segundos = new JTextField("ingrese segundos");
     segundos.setBounds(450,50,100,50);
     segundos.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
             if(e.getKeyCode() == KeyEvent.VK_ENTER){
                 int limite = Integer.parseInt(segundos.getText());
                 cron.setLimiteSegundos(limite);
                 cron.iniciar();
             }
         }
     });

        this.add(segundos);

        JTextField minutoss = new JTextField("ingrese minutos");
        minutoss.setBounds(450,200,100,50);
        minutoss.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    int limite = Integer.parseInt(minutoss.getText());
                    cron.setLimiteMinutos(limite);
                    cron.iniciar();
                }
            }
        });

     this.add(minutoss);




    }




}



public class Principal {

    public static void main(String[] args) {
        Ventana vent = new Ventana();
        vent.setVisible(true);

    }


}