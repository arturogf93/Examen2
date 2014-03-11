package examen2;


import java.awt.Image;
import java.awt.Toolkit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Abraham
 */
public class Piso extends Base {

    public Piso(int posX, int posY) {
        super(posX, posY);
        Image imag1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Piso1.png"));
        Image imag2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Piso2.png"));
        Image imag3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Piso3.png"));
        Image imag4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Piso4.png"));
        Image imag5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Piso5.png"));
        Image imag6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Piso6.png"));
        Image imag7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Piso7.png"));
        Image imag8 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Piso8.png"));
        Image imag9 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Piso9.png"));
        Image imag10 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Piso10.png"));
        Image imag11 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Piso11.png"));
        Image imag12 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Piso12.png"));
        Image imag13 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Piso13.png"));
        (this.getImagenes()).sumaCuadro(imag1, 18);
        (this.getImagenes()).sumaCuadro(imag2, 18);
        (this.getImagenes()).sumaCuadro(imag3, 18);
        (this.getImagenes()).sumaCuadro(imag4, 18);
        (this.getImagenes()).sumaCuadro(imag5, 18);
        (this.getImagenes()).sumaCuadro(imag6, 18);
        (this.getImagenes()).sumaCuadro(imag7, 18);
        (this.getImagenes()).sumaCuadro(imag8, 18);
        (this.getImagenes()).sumaCuadro(imag9, 18);
        (this.getImagenes()).sumaCuadro(imag10, 18);
        (this.getImagenes()).sumaCuadro(imag11, 18);
        (this.getImagenes()).sumaCuadro(imag12, 18);
        (this.getImagenes()).sumaCuadro(imag13, 18);

        
    }
    
}
