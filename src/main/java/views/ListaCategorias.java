package views;

import entity.CategoriaDTO;
import presenter.PresentadorCategoria;
import presenter.VistaCategorias;
import views.components.MiModeloDatosSoloLectura;
import views.helper.Categorias;
import views.helper.SwgAuxiliar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
/**
 * Formulario que muestra lista todos los registros asociados a una consulta relacionada con la tabla categorías
 * @author AGE
 * @version 2
 */

public class ListaCategorias extends JInternalFrame implements VistaCategorias,MouseListener, FocusListener, KeyListener,ActionListener {
    private static final int WIDTH = 225;
    private static final int HEIGHT = 200;
    private List<CategoriaDTO> categorias;
    private PresentadorCategoria presentador;
    private JMenuItem miFicha;


    {
        miFicha=new JMenuItem("Ficha");
        miFicha.setMnemonic('F');
        miFicha.addActionListener(this);
    }
    private JMenuItem miNuevo;{
        miNuevo=new JMenuItem("Nuevo");
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
    private JTable jTable;{
        jTable=new JTable();
        jTable.addMouseListener(this);
        jTable.addFocusListener(this);
        jTable.addKeyListener(this);
        jTable.setFillsViewportHeight(true);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    private JScrollPane scrollPane;

    @Override
    public void setCategorias(List<CategoriaDTO> listaCategorias) {
        this.categorias=listaCategorias;
        muestraTabla();
    }

    @Override
    public CategoriaDTO getCategoria() {
        return categorias.get(jTable.getSelectedRow());
    }
    @Override
    public void lanzar() {
        setVisible(true);
    }

    @Override
    public void setPresentador(PresentadorCategoria presentador) throws Exception {
        this.presentador=presentador;
        presentador.listaAllCategorias();
    }

    public ListaCategorias()  {
        scrollPane=new JScrollPane(jTable);
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
        setTitle("Listado de categorias:");
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

    public void muestraTabla()  {
        String[] nombreColumnas={"Código","Categoría"};
        Object datos [][]= new Object[categorias.size()][nombreColumnas.length];
        int i=0;
        for (CategoriaDTO categoria:categorias){
            datos[i][0]=categoria.getId();
            datos[i][1]=categoria.getCategoria();
            i++;
        }
        jTable.setModel(new MiModeloDatosSoloLectura(datos,nombreColumnas));
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent().equals(jTable)){
            if (e.getClickCount()==2)
                muestraFicha(getCategoria());
            else if (e.getButton()==MouseEvent.BUTTON3)
                jPopupMenu.show (jTable, e.getX (), e.getY ());
        }
    }

    private void muestraFicha(CategoriaDTO categoria) {
        try {
            FormMain.getInstance().getDesktopPane().add(Categorias.fichaCategoria(categoria));
            FormMain.getInstance().getDesktopPane().selectFrame(false);
        } catch (Exception e) {
            SwgAuxiliar.msgExcepcion(e);
        }
    }
    private void borrar() {
        if (JOptionPane.showConfirmDialog(this,
                String.format("¿Desea BORRAR la categoría: %s?",getCategoria().getCategoria()),
                "Atención:",
                JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
            try {
                presentador.borra();
                FormMain.actualizaListaCategorias();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,e.getMessage(),"Error: ",JOptionPane.ERROR_MESSAGE);
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
    public void focusGained(FocusEvent e) {
        if (e.getComponent().equals(jTable)) {
            FormMain.barraEstado("Realice un doble click o pulse espacio sobre la fila o celda para ver su ficha detalle");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ESCAPE)
            dispose();
        else if (e.getKeyCode()==KeyEvent.VK_SPACE)
            muestraFicha(getCategoria());
        else if (e.getKeyCode()==KeyEvent.VK_DELETE)
            borrar();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(miFicha))
            muestraFicha(getCategoria());
        else if (e.getSource().equals(miNuevo))
            muestraFicha(new CategoriaDTO());
        else if (e.getSource().equals(miBorra))
            borrar();
    }





}