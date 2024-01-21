package views;

import entity.CategoriaDTO;
import entity.LibroDTO;

import entity.UsuarioDTO;
import excepciones.CampoVacioExcepcion;

import presenter.PresentadorLibro;
import presenter.VistaLibros;
import views.components.MiModeloDatosSoloLectura;
import views.helper.Libros;
import views.helper.SwgAuxiliar;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

import java.util.List;
/**
 * Formulario que muestra lista todos los registros asociados a una consulta relacionada con la tabla libros
 * @author AGE
 * @version 2
 */

public class ListaLibros extends JInternalFrame implements VistaLibros, MouseListener, KeyListener, ActionListener, FocusListener {
    private static final int WIDTH = 625;
    private static final int HEIGHT = 500;
    private List<LibroDTO> libros;
    private PresentadorLibro presentador;
    private JTable jTable;{
        jTable=new JTable();
        jTable.addMouseListener(this);
        jTable.addFocusListener(this);
        jTable.addKeyListener(this);
        jTable.setFillsViewportHeight(true);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    private JScrollPane scrollPane;{
        scrollPane=new JScrollPane(jTable);
    }
    public void muestraTabla()  {
        String[] nombreColumnas={"ID","TITULO","EDITORIAL","AUTOR","CATEGORIA"};
        Object datos [][]= new Object[libros.size()][nombreColumnas.length];
        int i=0;
        for (LibroDTO libro:libros){
            datos[i][0]=libro.getId();
            datos[i][1]=libro.getNombre();
            datos[i][2]=libro.getEditorial();
            datos[i][3]=libro.getAutor();
            datos[i][4]=libro.getCategoria();
            i++;
        }
        jTable.setModel(new MiModeloDatosSoloLectura(datos,nombreColumnas));
    }
    private JMenuItem miFicha;{
        miFicha = new JMenuItem("Ficha");
        miFicha.setMnemonic('F');
        miFicha.addActionListener(this);
    }
    private JMenuItem miNuevo;{
        miNuevo = new JMenuItem("Nuevo");
        miNuevo.setMnemonic('N');
        miNuevo.addActionListener(this);
    }
    private JMenuItem miBorra;{
        miBorra=new JMenuItem("Borra");
        miBorra.setMnemonic('B');
        miBorra.addActionListener(this);
    }
    private JPopupMenu jPopupMenu;{
        jPopupMenu = new JPopupMenu();
        jPopupMenu.add(miFicha);
        jPopupMenu.add(miNuevo);
        jPopupMenu.add(miBorra);
    }
    @Override
    public void setLibros(List<LibroDTO> libros) {
        this.libros=libros;
        muestraTabla();
    }

    @Override
    public LibroDTO getLibro() {
        return libros.get(jTable.getSelectedRow());
    }
    @Override
    public void lanzar() {
        setVisible(true);
    }

    @Override
    public void setPresentador(PresentadorLibro presentador) throws Exception {
        this.presentador=presentador;
        presentador.listaAllLibros();
    }

    @Override
    public void setCategorias(List<CategoriaDTO> categorias) {
        // sin implementación
    }

    public ListaLibros() throws SQLException, CampoVacioExcepcion, IOException {
        setVentana();
        setContenedores();
        addKeyListener(this);
    }

    private void setContenedores() {
        setLayout(new GridLayout());
        add(scrollPane);
    }

    private void setVentana() throws SQLException, CampoVacioExcepcion, IOException {
        getContentPane().setBackground(Color.WHITE);
        setTitle("Listado de libros:");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        Dimension dime = new Dimension(WIDTH, HEIGHT);
        setBounds(FormMain.posInterna(),FormMain.posInterna(), WIDTH, HEIGHT);
        setMinimumSize(dime);
        setSize(dime);
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);
        setClosable(true);
    }


    @Override
    public void focusGained(FocusEvent e) {
        if (e.getComponent().equals(jTable))
            FormMain.barraEstado("Realice un doble click o pulse espacio sobre la fila o celda para ver su ficha detalle");
    }

    @Override
    public void focusLost(FocusEvent e) {

    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent().equals(jTable)){
            if (e.getClickCount() == 2)
                muestraFicha(getLibro());
            else if (e.getButton() == MouseEvent.BUTTON3)
                jPopupMenu.show(this, e.getX(), e.getY());
        }
    }

    private void muestraFicha(LibroDTO libro) {
        try {
            FormMain.getInstance().getDesktopPane().add(Libros.fichaLibro(libro));
            FormMain.getInstance().getDesktopPane().selectFrame(false);
        } catch (Exception e) {
            SwgAuxiliar.msgExcepcion(e);
        }

    }

    private void borrar(LibroDTO libro) {
        if (JOptionPane.showConfirmDialog(this,
                String.format("¿Desea BORRAR el libro: %s %s?",libro.getNombre(),libro.getAutor()),
                "Atención:",
                JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
            try {
                presentador.borra();
                FormMain.actualizaListaLibros();
            } catch (Exception e) {
                SwgAuxiliar.msgExcepcion(e);
            }
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
        if (e.getKeyCode()==KeyEvent.VK_ESCAPE)
            dispose();
        else if (e.getKeyCode() == KeyEvent.VK_SPACE)
            muestraFicha(getLibro());
        else if (e.getKeyCode()==KeyEvent.VK_DELETE)
            borrar(getLibro());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(miFicha))
            muestraFicha(getLibro());
        else if (e.getSource().equals(miNuevo))
            muestraFicha(new LibroDTO());
        else if (e.getSource().equals(miBorra))
            borrar(getLibro());
    }



}
