package examen2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Abraham
 */
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Base extends Animacion {

    private int posX;                   //posicion de x del objeto
    private int posY;                   //posicion de y del objeto
    protected Animacion imagenes;       //Objeto de tipo animacion

    //@param  posX es para saber la posicion x de tipo <code>int</code>
    //@param  posY es para saber la posicion y detipo <code>int</code>
    public Base(int posX, int posY) {       //constructor del objeto
        this.posX = posX;
        this.posY = posY;
        this.imagenes = new Animacion();
    }

    //@param  posX es para saber la posicion x que se asignara de tipo <code>int</code>
    public void setPosX(int posX) {     //metodo para asignar valor a posX
        this.posX = posX;
    }

    //@param  posY es para saber la posicion y que se asignara de tipo <code>int</code>
    public void setPosY(int posY) {     //metodo para asignar valor a posY
        this.posY = posY;
    }

    //@return    regresa posX de tipo <code>int</code>
    public int getPosX() {              //metodo para obtener valor a posX
        return this.posX;
    }

    //@return    regresa posY de tipo <code>int</code>
    public int getPosY() {              //metodo para obtener valor a posy
        return this.posY;
    }

    //@return    regresa el ancho de tipo <code>int</code>
    public int getWidth() {             //metodo para obtener valor a width
        return (new ImageIcon(imagenes.getImagen())).getIconWidth();
    }

    //@return    regresa el alto de tipo <code>int</code>
    public int getHeight() {            //metodo para obtener valor a height
        return (new ImageIcon(imagenes.getImagen())).getIconHeight();
    }

    //@return    regresa imagenes de tipo <code>Animacion</code>
    public Animacion getImagenes() {    //metodo para obtener la animacion del objeto
        return this.imagenes;
    }

    //@return    regresa el area de la imagen de tipo <code>Rectangle</code>
    public Rectangle getPerimetro() {   //metodo para obtener un rectangulo de la imagen actual
        return new Rectangle(getPosX(), getPosY(), getWidth(), getHeight());
    }

    //@param  obj es para conocer el objeto con el que se checara si se intersectade tipo <code>Base</code>
    //@return    regresa booleano de si choca o no de tipo <code>Boolean</code>
    public boolean intersecta(Base obj) {   //metodo para saber si chocaron dos objetos
        return getPerimetro().intersects(obj.getPerimetro());
    }

    //@return    regresa la imagen actual de la animacion  de tipo <code>Image</code>
    public Image getImagen() {              //metodo para obtener la imagen acutal
        return (this.getImagenes()).getImagen();
    }

    //@param  x es para conocer la posicion d x del punto tipo <code>int</code>
    //@param  y es para conocer la posicion d x del punto tipo <code>int</code>
    //@return regresa booleano de si esta dentro el punto o no <code>Boolean</code>
    public boolean dentro(int x, int y) {
        return this.getPerimetro().contains(x, y);
    }
}
