package examen2;

//hola
/**
 *
 * @author Oscar Abraham Rodriguez Quintanilla, Arturo Armando Gonzalez
 * Fernandez
 */
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Juego extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    public Juego() throws IOException {
        init();
        start();
    }

    private Graphics dbg;               //Objeto tipo Graphics
    private Image dbImage;              //Imagen para el doblebuffer 
    private long tiempoActual;          //Long para el tiempo del applet

    private Piso piso;
    private Flappy flappy;

    private Image fondo1;               //imagen del principio
    private Image tuboA;
    private Image tuboB;
    private Image tapTap;
    private Image getReady;
    //private Image titulo;
    private Image pierdes;
    private Image playAgain;
    private Image iconoPausa;

    private SoundClip fly;
    private SoundClip choque;
    private SoundClip bing;

    private int vy;
    private int gravedad;
    private int avance;
    private int contgravedad;
    private int hScore;
    private int nivel;

    private boolean pausa;              //Booleando para pausa
    private boolean inicio;
    private boolean gameover;
    private boolean juega;
    private boolean nivel1;
    private boolean nivel2;
    private boolean nivel3;
    private boolean choquesonido;
    private boolean guarda;             //flag para saber si se guarda
    private boolean carga;
    private boolean newScore;
    private boolean nivelMensaje;

    private File archivo;               //File para el archivo a crear
    private FileReader fr;              //fileReader para la lectura del archivo
    private BufferedReader br;
    private FileWriter file;            //un FileWriter para el manejo de archivos
    private PrintWriter out;

    private LinkedList<BaseEnemigo> tubosA;
    private LinkedList<BaseEnemigo> tubosB;

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>JFrame</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Jframe</code> y se definen funcionalidades.
     */
    public void init() throws IOException {
        nivel1 = true;
        nivel2 = false;
        nivel3 = false;
        juega = false;
        contgravedad = 1;
        gravedad = 1;
        hScore = 0;
        nivel = 1;
        this.setSize(286, 510);
        addKeyListener(this);           //Uso de las teclas
        addMouseListener(this);          //Uso de las teclas
        addMouseMotionListener(this);      //Uso de las teclas
        inicio = true;
        pausa = false;
        gameover = false;
        choquesonido = false;
        guarda = false;
        carga = false;
        newScore = false;
        nivelMensaje = false;
        fondo1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background1.png"));
        //titulo = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Titulo.png"));
        tuboA = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/TuboArriba.png"));
        tuboB = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/TuboAbajo.png"));
        tapTap = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/TapTap.png"));
        getReady = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/GetReady.png"));
        pierdes = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/GameOver.png"));
        playAgain = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/PlayAgain.png"));
        iconoPausa = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/pausa.png"));
        fly = new SoundClip("Sounds/fly.wav");
        choque = new SoundClip("Sounds/choque.wav");
        bing = new SoundClip("Sounds/bing.wav");
        piso = new Piso(0, this.getHeight() - 112);
        piso.setPosY(this.getHeight() - piso.getHeight());
        tubosA = new LinkedList();
        tubosB = new LinkedList();

        file = new FileWriter("hola.txt");
        out = new PrintWriter(file);
        archivo = new File("hola.txt");
        fr = new FileReader(archivo);
        br = new BufferedReader(fr);

        for (int i = 1; i <= 3; i++) {
            int yfinal = 0;
            int y = 0;
            if (nivel1) {
                y = ((int) (Math.random() * 100)) + 155;
                yfinal = this.getHeight() - y - 455 - ((int) (Math.random() * 30));
            } else if (nivel2) {
                y = ((int) (Math.random() * 120)) + 155;
                yfinal = this.getHeight() - y - 430 - ((int) (Math.random() * 30));
            } else if (nivel3) {
                y = ((int) (Math.random() * 140)) + 155;
                yfinal = this.getHeight() - y - 395 - ((int) (Math.random() * 30));
            }

            tubosA.add(new BaseEnemigo(this.getWidth() + (160 * i), yfinal, tuboA));
            tubosB.add(new BaseEnemigo(this.getWidth() + (160 * i), this.getHeight() - y, tuboB));
        }
        flappy = new Flappy(-40, -40);
        flappy.setPosX(this.getWidth() / 2 - 80);
        flappy.setPosY((this.getHeight() / 2 - flappy.getHeight() / 2) - 30);

    }

    /**
     * Metodo <I>start</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo para la animacion este metodo
     * es llamado despues del init o cuando el usuario visita otra pagina y
     * luego regresa a la pagina en donde esta este <code>Applet</code>
     *
     */
    public void start() {
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
     * incrementa la posicion en x o y dependiendo de la direccion, finalmente
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     *
     */
    public void run() {
        while (true) {
            if (!pausa) {
                try {
                    actualiza();
                } catch (IOException ex) {
                    Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
                }
                checaColision();
            }
            repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.
            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }

    /**
     * Metodo usado para actualizar la posicion de objetos elefante y raton.
     *
     */
    public void actualiza() throws IOException {
        //if(movimiento){
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;

        //Guarda el tiempo actual
        tiempoActual += tiempoTranscurrido;

        //Actualiza la animación en base al tiempo transcurrido
        //(bola.getImagenes()).actualiza(tiempoActual);
        if (!pausa && !gameover) {
            (piso.getImagenes()).actualiza(tiempoActual);
            (flappy.getImagenes()).actualiza(tiempoActual);

            if (juega) {
                avance = (vy + (gravedad * (contgravedad / 3)));
                contgravedad += 2;
                if (avance > 8) {
                    avance = 8;
                }
                flappy.setPosY(flappy.getPosY() + avance);
                if (flappy.getPosY() < 0) {
                    flappy.setPosY(0);
                    vy = 0;
                }
                for (int i = 0; i < tubosA.size(); i++) {
                    BaseEnemigo actualA = (BaseEnemigo) (tubosA.get(i));
                    BaseEnemigo actualB = (BaseEnemigo) (tubosB.get(i));
                    actualA.setPosX(actualA.getPosX() - 2);
                    actualB.setPosX(actualA.getPosX() - 2);
                    if (actualA.getPosX() == flappy.getPosX() + 1) {
                        flappy.setScore(flappy.getScore() + 1);
                        bing.play();
                    }
                }
            }
            if (flappy.getScore() > 8 && flappy.getScore() <= 18) {
                nivel1 = false;
                nivel2 = true;
                nivel3 = false;
            }
            if (flappy.getScore() > 18) {
                nivel1 = false;
                nivel2 = false;
                nivel3 = true;
            }

            if (flappy.getScore() >= 0 && flappy.getScore() <= 2 && nivel1) {
                nivel = 1;
                nivelMensaje = true;
            } else if (flappy.getScore() > 2 && nivel1) {
                nivelMensaje = false;
            }

            if (flappy.getScore() > 9 && flappy.getScore() <= 11 && nivel2) {
                nivel = 2;
                nivelMensaje = true;
            } else if (flappy.getScore() > 10 && nivel2) {
                nivelMensaje = false;
            }

            if (flappy.getScore() > 19 && flappy.getScore() <= 22 && nivel3) {
                nivel = 3;
                nivelMensaje = true;
            } else if (flappy.getScore() > 20 && nivel3) {
                nivelMensaje = false;
            }
        }

        if (guarda) {
            archivo = new File("guarda.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea;
            linea = br.readLine();
            System.out.println(linea);
            int highscore = Integer.parseInt(linea);
            if (highscore < flappy.getScore()) {
                file = new FileWriter("guarda.txt");
                out = new PrintWriter(file);
                out.println(flappy.getScore());
                hScore = flappy.getScore();
                newScore = true;
            } else {
                hScore = highscore;
            }
            file.close();
            guarda = false;
        }

    }

    /**
     * Metodo usado para checar las colisiones del objeto elefante y raton con
     * las orillas del <code>Applet</code>.
     */
    public void checaColision() {
        for (int i = 0; i < tubosA.size(); i++) {
            BaseEnemigo actualA = (BaseEnemigo) (tubosA.get(i));
            BaseEnemigo actualB = (BaseEnemigo) (tubosB.get(i));
            if (actualA.getPosX() + actualA.getWidth() < 0) {
                int y = 0;
                if (nivel1) {
                    y = ((int) (Math.random() * 100)) + 155;
                    actualA.setPosY(this.getHeight() - y - 455 - ((int) (Math.random() * 30)));
                } else if (nivel2) {
                    y = ((int) (Math.random() * 120)) + 155;
                    actualA.setPosY(this.getHeight() - y - 430 - ((int) (Math.random() * 30)));
                } else if (nivel3) {
                    y = ((int) (Math.random() * 140)) + 155;
                    actualA.setPosY(this.getHeight() - y - 395  - ((int) (Math.random() * 30)));
                }
                actualB.setPosY(this.getHeight() - y);
                actualA.setPosX(446);
                actualB.setPosX(446);
            }
            if ((actualA.intersecta(flappy) || actualB.intersecta(flappy)) && !choquesonido) {
                juega = false;
                gameover = true;
                pausa = false;
                inicio = false;
                choque.play();
                choquesonido = true;
                guarda = true;
            }
        }
        if (flappy.intersecta(piso) && !choquesonido) {
            juega = false;
            gameover = true;
            choque.play();
            choquesonido = true;
            guarda = true;
        }

    }

    /**
     * Metodo <I>update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }

    /**
     * Metodo <I>keyPressed</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar cualquier la
     * tecla.
     *
     * @param e es el <code>evento</code> generado al presionar las teclas.
     */
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Metodo <I>keyTyped</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que
     * no es de accion.
     *
     * @param e es el <code>evento</code> que se genera en al presionar las
     * teclas.
     */
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla
     * presionada.
     *
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {  //cuando el usuario presiona p
            if (!inicio){
                if (pausa) {
                    pausa = false;
                } else {
                    pausa = true;
                }
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            fly.stop();
            if (inicio) {
                inicio = false;
            }
            if (!juega) {
                juega = true;
            }
            contgravedad = 0;
            vy = -7;
            if (juega&&!pausa&&!gameover)
            fly.play();
        }
        if (e.getKeyCode() == KeyEvent.VK_G) {  //dejo de presionar la tecla de arriba
            if (!guarda) {
                guarda = true;
            }
        }

    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {
        g.setFont(new Font("TimesRoman", Font.BOLD, 25));
        g.setColor(Color.WHITE);
        if (piso != null) {
            g.drawImage(fondo1, 0, 0, this);

            for (int i = 0; i < tubosA.size(); i++) {
                BaseEnemigo actualA = (BaseEnemigo) (tubosA.get(i));
                BaseEnemigo actualB = (BaseEnemigo) (tubosB.get(i));
                g.drawImage(actualA.getImagen(), actualA.getPosX(), actualA.getPosY(), this);
                g.drawImage(actualB.getImagen(), actualB.getPosX(), actualB.getPosY(), this);
            }
            g.drawImage(piso.getImagen(), piso.getPosX(), piso.getPosY(), this);
            if (inicio) {
                g.drawImage(tapTap, this.getWidth() / 2 - tapTap.getWidth(this) / 2, this.getHeight() / 2 - tapTap.getHeight(this) / 2, this);
                g.drawImage(getReady, this.getWidth() / 2 - getReady.getWidth(this) / 2, this.getHeight() / 2 - 120, this);
            }
            g.drawImage(flappy.getImagen(), flappy.getPosX(), flappy.getPosY(), this);
            if (gameover) {
                g.drawImage(pierdes, this.getWidth() / 2 - pierdes.getWidth(this) / 2, this.getHeight() / 2 - 130, this);
                g.drawImage(playAgain, this.getWidth() / 2 - playAgain.getWidth(this) / 2, this.getHeight() / 2, this);
                if (newScore) {
                    g.drawString("New Highscore " + hScore, 30, 230);
                } else {
                    g.drawString("Highscore " + hScore, 55, 230);
                }
            }
            if (nivelMensaje) {
                g.drawString("Nivel " + nivel, 10, 500);
            }
            if (pausa){
                g.drawImage(iconoPausa, this.getWidth() / 2 - iconoPausa.getWidth(this) / 2, this.getHeight() / 2 - iconoPausa.getHeight(this) / 2 - 20, this);
            }
            g.drawString("" + flappy.getScore(), this.getWidth() / 2 - 5, 100);
        } else {
            //Da un mensaje mientras se carga el dibujo	
            g.drawString("No se cargo la imagen..", 20, 20);
        }
    }

    /**
     * Metodo mouseClicked sobrescrito de la interface MouseListener. En este
     * metodo maneja el evento que se genera al hacer click con el mouse sobre
     * algun componente. e es el evento generado al hacer click con el mouse.
     */
    public void mouseClicked(MouseEvent e) {
        Rectangle boton = new Rectangle(this.getWidth() / 2 - playAgain.getWidth(this) / 2, this.getHeight() / 2, playAgain.getWidth(this), playAgain.getHeight(this));
        int clicX = e.getX();
        int clicY = e.getY();
        if (boton.contains(clicX, clicY)) {
            flappy.setPosX(this.getWidth() / 2 - 80);
            flappy.setPosY((this.getHeight() / 2 - flappy.getHeight() / 2) - 30);
            gameover = false;
            juega = false;
            pausa = false;
            inicio = true;
            nivel1 = true;
            nivel2 = false;
            nivel3 = false;
            flappy.setScore(0);
            int tam = tubosA.size();
            tubosA.clear();
            tubosB.clear();
            for (int i = 1; i <= tam; i++) {
                int y = ((int) (Math.random() * 90)) + 165;
                tubosA.add(new BaseEnemigo(this.getWidth() + (160 * i), this.getHeight() - y - 455  - ((int) (Math.random() * 30)), tuboA));
                tubosB.add(new BaseEnemigo(this.getWidth() + (160 * i), this.getHeight() - y, tuboB));
            }
            choquesonido = false;
            newScore = false;
        }
    }

    /**
     * Metodo mouseEntered sobrescrito de la interface MouseListener. En este
     * metodo maneja el evento que se genera cuando el mouse entra en algun
     * componente. e es el evento generado cuando el mouse entra en algun
     * componente.
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Metodo mouseExited sobrescrito de la interface MouseListener. En este
     * metodo maneja el evento que se genera cuando el mouse sale de algun
     * componente. e es el evento generado cuando el mouse sale de algun
     * componente.
     */
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Metodo mousePressed sobrescrito de la interface MouseListener. En este
     * metodo maneja el evento que se genera al presionar un botÃ³n del mouse
     * sobre algun componente. e es el evento generado al presionar un botÃ³n
     * del mouse sobre algun componente.
     */
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Metodo mouseReleased sobrescrito de la interface MouseListener. En este
     * metodo maneja el evento que se genera al soltar un botÃ³n del mouse sobre
     * algun componente. e es el evento generado al soltar un botÃ³n del mouse
     * sobre algun componente.
     */
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Metodo mouseDragged sobrescrito de la interface MouseMotionListener. En
     * este metodo maneja el evento que se genera al mover un el mouse despues
     * de dar clic y no soltarlo
     */
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Metodo mouseMoved sobrescrito de la interface MouseMotionListener. En
     * este metodo maneja el evento que se genera al mover el mouse
     */
    public void mouseMoved(MouseEvent e) {

    }

}
