package views;



import entity.BusquedaLibro;
import entity.CategoriaDTO;
import entity.LibroDTO;
import presenter.PresentadorLibro;
import presenter.VistaLibros;
import views.components.MiModeloDatosSoloLectura;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
/**
 * JDialogo MODAL que muestra lista todos los registros asociados a una consulta relacionada con la tabla Libros
 * @author AGE
 * @version 2
 */
public class SeleccionaLibro extends JDialog implements VistaLibros, FocusListener, KeyListener, ActionListener,MouseListener {
    private static final int WIDTH = 625;
    private static final int HEIGHT = 200;
    private final BusquedaLibro busquedaLibro;
    private JTable jTable;

    {
        jTable = new JTable();
        jTable.addMouseListener(this);
        jTable.addFocusListener(this);
        jTable.addKeyListener(this);
        jTable.setFillsViewportHeight(true);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private JScrollPane scrollPane=new JScrollPane(jTable);
    private List<LibroDTO> libros;
    private PresentadorLibro presentador;
    private JMenuItem miSelecciona;{
        miSelecciona = new JMenuItem("Selecciona");
        miSelecciona.setMnemonic('S');
        miSelecciona.addActionListener(this);
    }

    private JPopupMenu jPopupMenu; {
        jPopupMenu = new JPopupMenu();
        jPopupMenu.add(miSelecciona);
    }

    @Override
    public void lanzar() {
        setVisible(true);

    }

    @Override
    public void setPresentador(PresentadorLibro presentador) throws Exception {
        this.presentador=presentador;
        switch (busquedaLibro.tipo){
            case OR:
                presentador.leerLibrosOR(busquedaLibro.id,busquedaLibro.titulo,busquedaLibro.autor,busquedaLibro.editorial,busquedaLibro.categoria);
                //presentador.listaAllLibros();
                break;
            default: presentador.listaAllLibros();
        }
    }

    @Override
    public LibroDTO getLibro() {
        return libros.get(jTable.getSelectedRow());
    }

    @Override
    public void setCategorias(List<CategoriaDTO> categorias) {
        //nada a implementar
    }

    @Override
    public void setLibros(List<LibroDTO> listaLibros) {
        this.libros=listaLibros;
        muestraTabla();

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

    public SeleccionaLibro(Frame owner, String title, boolean modal, BusquedaLibro busquedaLibro) {
        super(owner,title,modal);
        this.busquedaLibro=busquedaLibro;
        setVentana();
        setContenedores();
        addKeyListener(this);
    }

    private void setVentana() {
        getContentPane().setBackground(Color.WHITE);
        setTitle("Listado de libros:");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        Dimension dime = new Dimension(WIDTH, HEIGHT);
        setBounds(FormMain.posInterna(),FormMain.posInterna(), WIDTH, HEIGHT);
        setMinimumSize(dime);
        setSize(dime);
        setResizable(true);
        setLocationRelativeTo(null);
    }

    private void setContenedores() {
        setLayout(new GridLayout());
        add(scrollPane);
    }

    private void selecciona() {
        if (jTable.getSelectedRow()>-1){
            busquedaLibro.idSel=getLibro().getId();
            dispose();
        }
    }
    @Override
    public void focusGained(FocusEvent e) {
        if (e.getComponent().equals(jTable))
            FormMain.barraEstado("Realice un doble click o pulse espacio sobre la fila o celda para seleccionar el libro");
    }

    @Override
    public void focusLost(FocusEvent e) {

    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent().equals(jTable)){
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
        if (e.getKeyCode()==KeyEvent.VK_ESCAPE)
            dispose();
        else if (e.getKeyCode()==KeyEvent.VK_SPACE)
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
}
