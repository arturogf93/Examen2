/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package examen2;

import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author Abraham
 */
public class Flappy extends Base {

        private static int score;
    //@param  posX es para saber la posicion x de tipo <code>int</code>
    //@param  posY es para saber la posicion y de tipo <code>int</code>
    public Flappy(int posX, int posY) {
        super(posX, posY);
        Image imag1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Flappy1.png"));
        Image imag2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Flappy2.png"));
        Image imag3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Flappy3.png"));
        (this.getImagenes()).sumaCuadro(imag1, 70);
        (this.getImagenes()).sumaCuadro(imag2, 70);
        (this.getImagenes()).sumaCuadro(imag3, 70);
    }
    //@param  score es para conocer el nuevo puntaje que se le dara <code>int</code>
    public void setScore(int score) {
        this.score = score;
    }
    //@return    regresa el score de tipo <code>int</code>
    public int getScore() {
        return this.score;
    }
    
}
