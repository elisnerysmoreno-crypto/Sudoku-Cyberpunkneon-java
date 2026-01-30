/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegosudoku;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.border.AbstractBorder;

class BordeRedondeadoNeon extends AbstractBorder {
    private Color color;
    private int radio;
    private int grosor;

    public BordeRedondeadoNeon(Color color, int radio, int grosor) {
        this.color = color;
        this.radio = radio;
        this.grosor = grosor;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(grosor));
        // Dibujamos el arco del borde
        g2d.drawRoundRect(x + grosor/2, y + grosor/2, width - grosor, height - grosor, radio, radio);
    }
}