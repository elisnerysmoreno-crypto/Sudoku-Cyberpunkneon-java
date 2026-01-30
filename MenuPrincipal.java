
package juegosudoku;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.border.AbstractBorder;

public class MenuPrincipal extends JFrame {

    // Colores del tema
  private final Color Color_fondo = new Color(30, 30, 46);      
private final Color Color_Casinegro = new Color(24, 24, 37);     
private final Color Color_azul_vivo = new Color(88, 101, 242); 
private final Color Color_texto = new Color(205, 214, 244);
    public MenuPrincipal() {
    setTitle("Sudoku - Menú Principal");
    setSize(1000, 700); 
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    PanelConFondo contenedorPrincipal = new PanelConFondo("/Imagenessudoku/Fondo_sudoku.jpg"); 
    contenedorPrincipal.setLayout(new GridBagLayout()); 
    setContentPane(contenedorPrincipal);
    JPanel panelMenu = new JPanel();
    panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
    panelMenu.setOpaque(false); 

    // botones
    JButton btnContinuar = new JButton("CONTINUAR PARTIDA");
    JButton btnFacil = new JButton("NIVEL FÁCIL");
    JButton btnMedio = new JButton("NIVEL MEDIO");
    JButton btnDificil = new JButton("NIVEL DIFÍCIL");
    JButton btnSalir = new JButton("SALIR");

    // estilizar botones pero con letras negras
   estilizarBoton(btnContinuar, 20);
   estilizarBoton(btnFacil, 18);
   estilizarBoton(btnMedio, 18);
   estilizarBoton(btnDificil, 18);
   estilizarBoton(btnSalir, 18);

    //añadir todo al panel (con espacios entre botones)
    
    panelMenu.add(Box.createRigidArea(new Dimension(0, 20))); 
    panelMenu.add(btnContinuar);
    panelMenu.add(Box.createRigidArea(new Dimension(0, 8)));
    panelMenu.add(btnFacil);
    panelMenu.add(Box.createRigidArea(new Dimension(0, 8)));
    panelMenu.add(btnMedio);
    panelMenu.add(Box.createRigidArea(new Dimension(0, 8)));
    panelMenu.add(btnDificil);
    panelMenu.add(Box.createRigidArea(new Dimension(0, 15)));
    panelMenu.add(btnSalir);

    btnContinuar.addActionListener(e -> abrirJuego(0, true)); 
    btnFacil.addActionListener(e -> abrirJuego(20, false));
    btnMedio.addActionListener(e -> abrirJuego(40, false));
    btnDificil.addActionListener(e -> abrirJuego(60, false));
    btnSalir.addActionListener(e -> System.exit(0));
   
    add(panelMenu);
}

    private void estilizarBoton(JButton btn, int tamano) {
    Color rosaVibrante = new Color(255, 0, 127); 
    Color verdeNeon = new Color(57, 255, 20);    
    Color negroSuavizado = new Color(25, 25, 25); 
    
    btn.setContentAreaFilled(false);
    btn.setOpaque(false);           

    btn.setBackground(negroSuavizado);
    btn.setForeground(rosaVibrante);
    btn.setFont(new Font("Arial Black", Font.BOLD, tamano - 2));
    btn.setFocusPainted(false);
    btn.setBorder(new BordeRedondeadoNeon(verdeNeon, 20, 2));
    
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    btn.setMaximumSize(new Dimension(250, 40));
    btn.setPreferredSize(new Dimension(250, 40));

    btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Dibujar el fondo redondeado manualmente
            g2d.setColor(c.getBackground());
            g2d.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
            
            super.paint(g2d, c);
        }
    });

    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            btn.setBackground(new Color(45, 45, 45)); 
            btn.setBorder(new BordeRedondeadoNeon(rosaVibrante, 20, 2));
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            btn.setBackground(negroSuavizado);
            btn.setBorder(new BordeRedondeadoNeon(verdeNeon, 20, 2));
        }
    });
}

    private void abrirJuego(int vacios, boolean cargar) {
        new SudokuGUIU(vacios, cargar).setVisible(true);
    }
 class PanelConFondo extends JPanel {
    private Image imagen;

    public PanelConFondo(String ruta) {
        
        try {
            java.net.URL imgURL = getClass().getResource(ruta);
            if (imgURL != null) {
                this.imagen = new ImageIcon(imgURL).getImage();
            } else {
                System.err.println("No se encontró la imagen en: " + ruta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

 
    public static void main(String[] args) {
        try {
            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}