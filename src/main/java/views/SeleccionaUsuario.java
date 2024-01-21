package views;


import entity.BusquedaUsuario;
import entity.UsuarioDTO;
import presenter.PresentadorUsuario;
import presenter.VistaUsuarios;
import views.components.MiModeloDatosSoloLectura;

import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import java.awt.*;

/**
 * JDialogo MODAL que muestra lista todos los registros asociados a una consulta relacionada con la tabla usuarios
 *
 * @author AGE
 * @version 2
 */
public class SeleccionaUsuario extends JDialog implements VistaUsuarios, FocusListener, KeyListener, ActionListener, MouseListener {
    private static final int WIDTH = 625;
    private static final int HEIGHT = 200;
    private final BusquedaUsuario busquedaUsuario;

    private List<UsuarioDTO> usuarios;
    private PresentadorUsuario presentador;
    private JTable jTable;

    {
        jTable = new JTable();
        jTable.addMouseListener(this);
        jTable.addFocusListener(this);
        jTable.addKeyListener(this);
        jTable.setFillsViewportHeight(true);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private JScrollPane scrollPane = new JScrollPane(jTable);
    private JMenuItem miSelecciona;

    {
        miSelecciona = new JMenuItem("Selecciona");
        miSelecciona.setMnemonic('S');
        miSelecciona.addActionListener(this);
    }

    private JPopupMenu jPopupMenu;

    {
        jPopupMenu = new JPopupMenu();
        jPopupMenu.add(miSelecciona);
    }

    @Override
    public void lanzar() {
        setVisible(true);
    }

    @Override
    public void setPresentador(PresentadorUsuario presentador) throws Exception {
        this.presentador = presentador;
        switch (busquedaUsuario.tipo) {
            case OR:
                presentador.leerUsuariosOR(busquedaUsuario.id,busquedaUsuario.nombre,busquedaUsuario.apellidos);
                //presentador.listaAllUsuarios();
                break;
            default:
                presentador.listaAllUsuarios();
        }
    }

    @Override
    public UsuarioDTO getUsuario() {
        return usuarios.get(jTable.getSelectedRow());
    }


    public SeleccionaUsuario(Frame owner, String title, boolean modal, BusquedaUsuario busquedaUsuario) {
        super(owner, title, modal);
        this.busquedaUsuario = busquedaUsuario;
        setVentana();
        setContenedores();
        addKeyListener(this);
    }

    private void setContenedores() {
        setLayout(new GridLayout());
        add(scrollPane);
    }

    private void setVentana() {
        getContentPane().setBackground(Color.WHITE);
        setTitle("Listado de usuarios:");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        Dimension dime = new Dimension(WIDTH, HEIGHT);
        setBounds(FormMain.posInterna(), FormMain.posInterna(), WIDTH, HEIGHT);
        setMinimumSize(dime);
        setSize(dime);
        setResizable(true);
        setLocationRelativeTo(null);
    }

    private void selecciona() {
        if (jTable.getSelectedRow() > -1) {

            busquedaUsuario.idSel = getUsuario().getId();
            dispose();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getComponent().equals(jTable))
            FormMain.barraEstado("Realice un doble click o pulse espacio sobre la fila o celda para seleccionar el usuario");
    }

    @Override
    public void focusLost(FocusEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent().equals(jTable)) {
            if (e.getClickCount() == 2)
                selecciona();
            else if (e.getButton() == MouseEvent.BUTTON3)
                jPopupMenu.show(this, e.getX(), e.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            dispose();
        else if (e.getKeyCode() == KeyEvent.VK_SPACE)
            selecciona();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(miSelecciona))
            selecciona();
    }

    @Override
    public void setUsuarios(List<UsuarioDTO> listaUsuarios) {
        this.usuarios = listaUsuarios;
        muestraTabla();

    }

    public void muestraTabla() {
        String[] nombreColumnas = {"ID", "NOMBRE", "APELLIDOS"};
        Object datos[][] = new Object[usuarios.size()][nombreColumnas.length];
        int i = 0;
        for (UsuarioDTO usuario : usuarios) {
            datos[i][0] = usuario.getId();
            datos[i][1] = usuario.getNombre();
            datos[i][2] = usuario.getApellidos();
            i++;
        }
        jTable.setModel(new MiModeloDatosSoloLectura(datos, nombreColumnas));
    }
}
