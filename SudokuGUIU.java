/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegosudoku;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.*;


public class SudokuGUIU extends JFrame {
    private JTextField[][] campos = new JTextField[9][9];
    private int[][] solucion = new int[9][9];
    private int[][] retoInicial = new int[9][9];
    private int puntos = 0;
    private JLabel lblPuntos;
    private int vacios;
private boolean autoLlenando = false;
private Timer cronometro;
private int tiempoRestante; // en segundos
private JLabel lblCronometro;
    // Colores del tema 
  
private final Color COLOR_HEADER = new Color(24, 24, 37);
private final Color COLOR_FONDO_APP = new Color(30, 30, 46);
private final Color COLOR_CELDA_FIJA = new Color(49, 50, 68);    
private final Color COLOR_CELDA_EDIT = new Color(69, 71, 90);    
private final Color COLOR_TEXTO_FIJO = new Color(137, 180, 250); 
private final Color COLOR_TEXTO_USER = Color.WHITE;               
private final Color COLOR_VERDE_NEON = new Color(166, 227, 161); 
private final Color COLOR_ROJO_VIVO = new Color(243, 139, 168);  

    public SudokuGUIU(int dificultad, boolean cargarGuardado) {
        this.vacios = dificultad;
        setTitle("Sudoku Pro - Partida en Curso");
        setSize(850, 750);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_FONDO_APP); 

        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(COLOR_HEADER);
        panelHeader.setBorder(new EmptyBorder(15,0,15,0));
        lblPuntos = new JLabel("Puntuación: 0", SwingConstants.CENTER);
        lblPuntos.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblPuntos.setForeground(Color.WHITE);
        add(panelHeader, BorderLayout.NORTH);
lblCronometro = new JLabel("TIEMPO: 00:00");
lblCronometro.setFont(new Font("Monospaced", Font.BOLD, 24));
lblCronometro.setForeground(new Color(0, 255, 255)); 

panelHeader.add(lblPuntos);     //puntos
panelHeader.add(lblCronometro);
     
iniciarCronometro(); // Activa el conteo

switch(vacios) { 
    case 20: tiempoRestante = 1200; break; 
    case 40: tiempoRestante = 900;  break; 
    case 60: tiempoRestante = 600;  break; 
    default: tiempoRestante = 1000;
}
  //panel central
JPanel panelTableroExterno = new JPanel(new BorderLayout()); 
panelTableroExterno.setBackground(COLOR_FONDO_APP);
panelTableroExterno.setBorder(new EmptyBorder(20, 20, 20, 20));

JPanel panelTableroInterno = new JPanel(new GridLayout(9, 9));
panelTableroInterno.setBorder(new BordeRedondeadoNeon(new Color(57, 255, 20), 0, 4));

if (cargarGuardado) {
    cargarProgreso();
} else {
    inicializarNuevoJuego();
}

dibujarTablero(panelTableroInterno);


panelTableroExterno.add(panelTableroInterno, BorderLayout.CENTER); 
add(panelTableroExterno, BorderLayout.CENTER);

        // Botones 
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 20));
        panelBotones.setBackground(COLOR_FONDO_APP);
        panelBotones.setBorder(new EmptyBorder(20, 10, 20, 20));

      JButton btnGuardar = new JButton(" Guardar");
JButton btnResolver = new JButton("Solución"); // 
JButton btnReiniciar = new JButton("Reiniciar");
JButton btnSalir = new JButton("Cerrar");    

estilizarBotonLateral(btnGuardar);
estilizarBotonLateral(btnResolver);  
estilizarBotonLateral(btnReiniciar);
estilizarBotonLateral(btnSalir);     

btnGuardar.addActionListener(e -> guardarProgreso());
btnResolver.addActionListener(e -> mostrarSolucion());
btnReiniciar.addActionListener(e -> reiniciarPartida());
btnSalir.addActionListener(e -> this.dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnResolver);
        panelBotones.add(btnReiniciar);
        panelBotones.add(btnSalir);
        add(panelBotones, BorderLayout.EAST);
    }

     // Método auxiliar para estilo de botones laterales
private void estilizarBotonLateral(JButton btn) {
    Color rosaVibrante = new Color(255, 0, 127); 
    Color verdeNeon = new Color(57, 255, 20);    
    Color negroSuavizado = new Color(25, 25, 25); 

    btn.setContentAreaFilled(false);
    btn.setOpaque(false);
    btn.setBackground(negroSuavizado);
    btn.setForeground(rosaVibrante);
    btn.setFont(new Font("Arial Black", Font.BOLD, 14)); 
    btn.setFocusPainted(false);
    
    // Borde redondeado neón verde
    btn.setBorder(new BordeRedondeadoNeon(verdeNeon, 15, 2));

    btn.setMaximumSize(new Dimension(120, 80));
    btn.setPreferredSize(new Dimension(120, 80));
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);

    btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(c.getBackground());
            g2d.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 15, 15);
            super.paint(g2d, c);
        }
    });

    // Efecto Hover
    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            btn.setBackground(new Color(45, 45, 45));
            btn.setBorder(new BordeRedondeadoNeon(rosaVibrante, 15, 2));
            btn.setForeground(verdeNeon);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            btn.setBackground(negroSuavizado);
            btn.setBorder(new BordeRedondeadoNeon(verdeNeon, 15, 2));
            btn.setForeground(rosaVibrante);
        }
    });
}
    private void inicializarNuevoJuego() {
        solucion = new int[9][9];
        retoInicial = new int[9][9];
        GeneradorSudoku.llenarTablero(solucion);
        for(int i=0; i<9; i++) System.arraycopy(solucion[i], 0, retoInicial[i], 0, 9);
        GeneradorSudoku.removerNumeros(retoInicial, vacios);
    }

private void dibujarTablero(JPanel panelTableroInterno) {
    panelTableroInterno.removeAll(); 
    
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
           
            final int filaFinal = i;
            final int colFinal = j;

            campos[i][j] = new JTextField(); 
            JTextField txt = campos[i][j];
            
            txt.setHorizontalAlignment(JTextField.CENTER);
            txt.setFont(new Font("Arial Black", Font.BOLD, 22));
            txt.setBackground(new Color(20, 20, 30)); 
            
            if (retoInicial[i][j] != 0) {
                txt.setText(String.valueOf(retoInicial[i][j]));
                txt.setEditable(false);
                txt.setForeground(new Color(57, 255, 20)); 
            } else {
                txt.setText("");
                txt.setEditable(true);
                txt.setForeground(new Color(0, 255, 255)); 

                txt.addKeyListener(new java.awt.event.KeyAdapter() {
                    @Override
                    public void keyReleased(java.awt.event.KeyEvent e) {
                        verificarEntrada(txt, filaFinal, colFinal); 
                    }
                });
            }

            int top = (filaFinal % 3 == 0) ? 3 : 1;
            int left = (colFinal % 3 == 0) ? 3 : 1;
            txt.setBorder(BorderFactory.createMatteBorder(top, left, 1, 1, new Color(57, 255, 20)));
            
            txt.setCaretColor(Color.WHITE);
            panelTableroInterno.add(txt);
        }
    }
    panelTableroInterno.revalidate();
    panelTableroInterno.repaint();
}
private void verificarEntrada(JTextField txt, int fila, int columna) {
    String valor = txt.getText().trim();
    if (valor.isEmpty()) return;

    try {
        int num = Integer.parseInt(valor);
        if (num == solucion[fila][columna]) {
            txt.setForeground(new Color(57, 255, 20));
            puntos += 10;
            
            //Bonus de tiempu
            tiempoRestante += 10; 
    
            
        } else {
            txt.setForeground(new Color(255, 0, 127)); 
            if (puntos >= 5) puntos -= 5;
        }
        lblPuntos.setText("Puntuación: " + puntos);
    } catch (NumberFormatException e) {
        txt.setText("");
    }
}

private void actualizarPuntaje() {
    lblPuntos.setText("Puntuación: " + puntos);
}

  private void agregarEventoValidacion(int f, int c) {
    campos[f][c].getDocument().addDocumentListener(new DocumentListener() {
        public void insertUpdate(DocumentEvent e) { validar(); }
        public void removeUpdate(DocumentEvent e) { validar(); }
        public void changedUpdate(DocumentEvent e) { }

        private void validar() {
            
            if (autoLlenando) return;
        
            SwingUtilities.invokeLater(() -> {
                String texto = campos[f][c].getText();
                if (texto.isEmpty()) return;

                try {
                    int num = Integer.parseInt(texto);
                    if (num == solucion[f][c]) {
                        campos[f][c].setForeground(COLOR_VERDE_NEON);
                        puntos += 10;
                        verificarVictoria(); 
                    } else {
                        campos[f][c].setForeground(COLOR_ROJO_VIVO);
                        puntos -= 5;
                    }
                    lblPuntos.setText("Puntuación: " + puntos);
                } catch (NumberFormatException ex) {
                 
                }
            });
        }
    });
}

    private void verificarVictoria() {
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                String txt = campos[f][c].getText();
                if (txt.isEmpty()) return; 
                try {
                     if (Integer.parseInt(txt) != solucion[f][c]) return;
                } catch (NumberFormatException e) { return; }
            }
        }
      
        JOptionPane.showMessageDialog(this, "¡FELICITACIONES! Has completado el Sudoku.\nPuntuación final: " + puntos, "¡Victoria!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void guardarProgreso() {
        try (PrintWriter out = new PrintWriter(new FileWriter("progreso.txt"))) {
            out.println(puntos);
            for (int f = 0; f < 9; f++) {
                for (int c = 0; c < 9; c++) out.print(solucion[f][c] + ",");
                out.println();
            }
            for (int f = 0; f < 9; f++) {
                for (int c = 0; c < 9; c++) out.print(retoInicial[f][c] + ",");
                out.println();
            }
            JOptionPane.showMessageDialog(this, "Partida guardada correctamente.", "Guardado", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarProgreso() {
        File archivo = new File("progreso.txt");
        if (!archivo.exists()) {
             JOptionPane.showMessageDialog(this, "No existe una partida guardada. Se iniciará una nueva.", "Aviso", JOptionPane.WARNING_MESSAGE);
             inicializarNuevoJuego();
             return;
        }

        try (BufferedReader in = new BufferedReader(new FileReader(archivo))) {
            puntos = Integer.parseInt(in.readLine());
            for (int f = 0; f < 9; f++) {
                String[] fila = in.readLine().split(",");
                for (int c = 0; c < 9; c++) solucion[f][c] = Integer.parseInt(fila[c]);
            }
            for (int f = 0; f < 9; f++) {
                String[] fila = in.readLine().split(",");
                for (int c = 0; c < 9; c++) retoInicial[f][c] = Integer.parseInt(fila[c]);
            }
             lblPuntos.setText("Puntuación: " + puntos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la partida. El archivo podría estar dañado.", "Error", JOptionPane.ERROR_MESSAGE);
            inicializarNuevoJuego();
        }
    }

private void mostrarSolucion() {
    int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro? Esto contará como un intento fallido.", "Ver Solución", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) return;

    autoLlenando = true; 
    puntos = 0; 
    lblPuntos.setText("Puntuación: 0 (Fallaste)");

    for (int f = 0; f < 9; f++) {
        for (int c = 0; c < 9; c++) {
            campos[f][c].setText(String.valueOf(solucion[f][c]));
            if (retoInicial[f][c] == 0) campos[f][c].setForeground(new Color(13, 110, 253));
        }
    }
    
    autoLlenando = false; 
    JOptionPane.showMessageDialog(this, "Se ha mostrado la solución. ¡Inténtalo de nuevo en una partida nueva!", "Fallaste", JOptionPane.ERROR_MESSAGE);
}

class BordeRedondeadoNeon extends javax.swing.border.AbstractBorder {
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
        g2d.drawRoundRect(x + grosor/2, y + grosor/2, width - grosor, height - grosor, radio, radio);
    }
}

private void iniciarCronometro() {
   
    if (cronometro != null && cronometro.isRunning()) {
        cronometro.stop();
    }

    cronometro = new Timer(1000, e -> {
        tiempoRestante--;
        
        int minutos = tiempoRestante / 60;
        int segundos = tiempoRestante % 60;
        
        // Usamos el label que ya está en el panel
        lblCronometro.setText(String.format("TIEMPO: %02d:%02d", minutos, segundos));

        if (tiempoRestante <= 0) {
            cronometro.stop();
            JOptionPane.showMessageDialog(this, "¡GAME OVER!");
            this.dispose();
        }
    });
    cronometro.start();
}

    private void reiniciarPartida() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Reiniciar el tablero actual? Tu puntuación volverá a 0.", "Reiniciar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        puntos = 0;
        lblPuntos.setText("Puntuación: 0");
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                if (retoInicial[f][c] == 0) {
                    campos[f][c].setText("");
                    campos[f][c].setForeground(Color.BLACK);
                }
            }
        }
    }
}