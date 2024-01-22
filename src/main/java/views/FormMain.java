package views;

import repository.helper.PatronObserverImpl;
import entity.LibroDTO;
import entity.PrestamosDTO;
import entity.UsuarioDTO;
import excepciones.CampoVacioExcepcion;
import entity.CategoriaDTO;
import repository.helper.Entidades;
import views.components.MiBarraDeEstado;
import views.helper.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Formulario principal de la aplicación, en el se implementarán las opciones
 * de menú necesarias para poder utilizar la aplicación de BIBLIOTECA
 *
 * @author AGE
 * @version 2
 */
public class FormMain extends JFrame implements ActionListener, FocusListener, WindowListener, KeyListener {
    private static FormMain main = null;
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 756;
    private JDesktopPane desktopPane = new JDesktopPane();
    private static PatronObserverImpl patron = new PatronObserverImpl();

    private JMenu mArchivo;

    {
        mArchivo = new JMenu("Archivo");
        mArchivo.setMnemonic('A');
    }

    private JMenuItem miAbrir;

    {
        miAbrir = new JMenuItem("Abrir..");
        miAbrir.setMnemonic('A');
        miAbrir.setFocusable(true);
        miAbrir.addActionListener(this);
        miAbrir.addFocusListener(this);
        //mArchivo.add(miAbrir); TODO pendiente de implementar
    }

    private JMenuItem miGuardarLibro;

    {
        miGuardarLibro = new JMenuItem("Guardar libros..");
        miGuardarLibro.setMnemonic('G');
        miGuardarLibro.setFocusable(true);
        miGuardarLibro.addActionListener(this);
        miGuardarLibro.addFocusListener(this);
        mArchivo.add(miGuardarLibro);
    }

    private JMenuItem miConexion;

    {
        miConexion = new JMenuItem("Conectar");
        miConexion.setMnemonic('C');
        miConexion.addActionListener(this);
        mArchivo.addSeparator();
        mArchivo.add(miConexion);
    }

    private JMenuItem miSalir;

    {
        miSalir = new JMenuItem("Salir");
        miSalir.setMnemonic('S');
        miSalir.setFocusable(true);
        miSalir.addActionListener(this);
        miSalir.addFocusListener(this);
        mArchivo.addSeparator();
        mArchivo.add(miSalir);
    }

    private JMenu mCategorias;

    {
        mCategorias = new JMenu("Categorias");
        mCategorias.setMnemonic('U');
        mCategorias.setFocusable(true);
        mCategorias.addFocusListener(this);
    }

    private JMenuItem miListaCategorias;

    {
        miListaCategorias = new JMenuItem("Lista");
        miListaCategorias.setMnemonic('L');
        miListaCategorias.setFocusable(true);
        miListaCategorias.addActionListener(this);
        miListaCategorias.addFocusListener(this);
        mCategorias.add(miListaCategorias);

    }

    private JMenuItem miNuevaCategoria;

    {
        miNuevaCategoria = new JMenuItem("Nuevo");
        miNuevaCategoria.setMnemonic('N');
        miNuevaCategoria.setFocusable(true);
        miNuevaCategoria.addActionListener(this);
        miNuevaCategoria.addFocusListener(this);
        mCategorias.add(miNuevaCategoria);
    }
    private JMenuItem exportarXlsxCategoria;

    {
        exportarXlsxCategoria = new JMenuItem("Excel...");
        exportarXlsxCategoria .setMnemonic('N');
        exportarXlsxCategoria .setFocusable(true);
        exportarXlsxCategoria .addActionListener(this);
        exportarXlsxCategoria .addFocusListener(this);
        mCategorias.add(exportarXlsxCategoria);

    }

    private JMenu mUsuarios;

    {
        mUsuarios = new JMenu("Usuarios");
        mUsuarios.setMnemonic('U');
        mUsuarios.setFocusable(true);
        mUsuarios.addFocusListener(this);
    }

    private JMenuItem miListaUsuarios;

    {
        miListaUsuarios = new JMenuItem("Lista");
        miListaUsuarios.setMnemonic('L');
        miListaUsuarios.setFocusable(true);
        miListaUsuarios.addActionListener(this);
        miListaUsuarios.addFocusListener(this);
        mUsuarios.add(miListaUsuarios);

    }

    private JMenuItem miNuevoUsuario;

    {
        miNuevoUsuario = new JMenuItem("Nuevo");
        miNuevoUsuario.setMnemonic('N');
        miNuevoUsuario.setFocusable(true);
        miNuevoUsuario.addActionListener(this);
        miNuevoUsuario.addFocusListener(this);
        mUsuarios.add(miNuevoUsuario);

    }
    private JMenuItem exportarXlsxUsuario;

    {
        exportarXlsxUsuario = new JMenuItem("Excel...");
        exportarXlsxUsuario .setMnemonic('N');
        exportarXlsxUsuario .setFocusable(true);
        exportarXlsxUsuario .addActionListener(this);
        exportarXlsxUsuario .addFocusListener(this);
        mUsuarios.add(exportarXlsxUsuario);

    }


    private JMenu mLibros;

    {
        mLibros = new JMenu("Libros");
        mLibros.setMnemonic('L');
        mLibros.setFocusable(true);
        mLibros.addFocusListener(this);
    }

    private JMenuItem miListaLibros;

    {
        miListaLibros = new JMenuItem("Lista");
        miListaLibros.setMnemonic('L');
        miListaLibros.setFocusable(true);
        miListaLibros.addActionListener(this);
        miListaLibros.addFocusListener(this);
        mLibros.add(miListaLibros);

    }

    private JMenuItem miNuevoLibro;

    {
        miNuevoLibro = new JMenuItem("Nuevo");
        miNuevoLibro.setMnemonic('N');
        miNuevoLibro.setFocusable(true);
        miNuevoLibro.addActionListener(this);
        miNuevoLibro.addFocusListener(this);
        mLibros.add(miNuevoLibro);
    }
    private JMenuItem exportarXlsxLibro;

    {
        exportarXlsxLibro = new JMenuItem("Excel...");
        exportarXlsxLibro .setMnemonic('N');
        exportarXlsxLibro .setFocusable(true);
        exportarXlsxLibro .addActionListener(this);
        exportarXlsxLibro .addFocusListener(this);
        mLibros.add(exportarXlsxLibro);

    }

    private JMenu mPrestamos;

    {
        mPrestamos = new JMenu("Préstamos");
        mPrestamos.setMnemonic('P');
        mPrestamos.setFocusable(true);
        mPrestamos.addFocusListener(this);
    }

    private JMenuItem miListaPrestamos;

    {
        miListaPrestamos = new JMenuItem("Lista");
        miListaPrestamos.setMnemonic('L');
        miListaPrestamos.setFocusable(true);
        miListaPrestamos.addActionListener(this);
        miListaPrestamos.addFocusListener(this);
        mPrestamos.add(miListaPrestamos);
    }

    private JMenuItem miNuevoPrestamo;

    {
        miNuevoPrestamo = new JMenuItem("Nuevo");
        miNuevoPrestamo.setMnemonic('N');
        miNuevoPrestamo.setFocusable(true);
        miNuevoPrestamo.addActionListener(this);
        miNuevoPrestamo.addFocusListener(this);
        mPrestamos.add(miNuevoPrestamo);
    }
    private JMenuItem exportarXlsxPrestamo;

    {
        exportarXlsxPrestamo = new JMenuItem("Excel...");
        exportarXlsxPrestamo .setMnemonic('N');
        exportarXlsxPrestamo .setFocusable(true);
        exportarXlsxPrestamo .addActionListener(this);
        exportarXlsxPrestamo .addFocusListener(this);
        mPrestamos.add(exportarXlsxPrestamo);

    }

    private JMenuBar jMenuBar;

    {
        jMenuBar = new JMenuBar();
        jMenuBar.add(mArchivo);
        jMenuBar.add(mCategorias);
        jMenuBar.add(mUsuarios);
        jMenuBar.add(mLibros);
        jMenuBar.add(mPrestamos);
        jMenuBar.addFocusListener(this);
    }

    private MiBarraDeEstado miBarraDeEstado;

    {
        miBarraDeEstado = MiBarraDeEstado.getInstance();
    }

    private FormMain() {
        setVentana();
        setContenedores();
        actualizaFormulario(true);
        addEventos();
    }

    private void addEventos() {
        addWindowListener(this);
        getContentPane().setFocusable(true);
        getContentPane().addKeyListener(this);
        getContentPane().addFocusListener(this);
    }

    private void setContenedores() {
        setLayout(new BorderLayout());
        add(jMenuBar, BorderLayout.NORTH);
        add(desktopPane, BorderLayout.CENTER);
        add(miBarraDeEstado, BorderLayout.SOUTH);
    }

    private void setVentana() {
        setTitle("Aplicación de gestión de una biblioteca: ");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(0, 0, WIDTH, HEIGHT);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public JDesktopPane getDesktopPane() {
        return desktopPane;
    }

    /**
     * Este método habilitará o desactivará las distintas
     * opciones de menú del programa según corresponda
     */
    public void actualizaFormulario(boolean conectado) {
        miConexion.setEnabled(!conectado);
        miAbrir.setEnabled(conectado);
        mCategorias.setEnabled(conectado);
        mUsuarios.setEnabled(conectado);
        mLibros.setEnabled(conectado);
        mPrestamos.setEnabled(conectado);
    }

    /**
     * Método para la implementación del Singleton del formulario principal
     *
     * @return el objeto global donde se instancia el formulario de la aplicación
     */
    public static FormMain getInstance() {
        if (main == null) {
            main = new FormMain();
            main.loginPassword();
        }
        return main;
    }

    private void loginPassword() {
        new LoginPass(this, "Conectar BD:", true).setVisible(true);
    }

    private void muestraCategorias() {
        try {
            desktopPane.add(Categorias.listaCategorias());
            desktopPane.selectFrame(false);
        } catch (Exception e) {
            SwgAuxiliar.msgExcepcion(e);
        }
    }

    private void nuevaCategoria() {
        try {
            desktopPane.add(Categorias.fichaCategoria(new CategoriaDTO()));
            desktopPane.selectFrame(false);
        } catch (Exception e) {
            SwgAuxiliar.msgExcepcion(e);
        }
    }

    private void muestraUsuarios() {
        try {
            desktopPane.add(Usuarios.listaUsuarios());

            desktopPane.selectFrame(false);
        } catch (Exception e) {
            SwgAuxiliar.msgExcepcion(e);
        }
    }

    private void nuevoUsuario() {
        try {
            desktopPane.add(Usuarios.fichaUsuario(new UsuarioDTO()));
            desktopPane.selectFrame(false);
        } catch (Exception e) {
            SwgAuxiliar.msgExcepcion(e);
        }
    }


    private void muestraLibros() {
        try {
            desktopPane.add(Libros.listaLibros());
            desktopPane.selectFrame(false);
        } catch (Exception e) {
            SwgAuxiliar.msgExcepcion(e);
        }
    }

    private void nuevoLibro() {
        try {
            desktopPane.add(Libros.fichaLibro(new LibroDTO()));
            desktopPane.selectFrame(false);
        } catch (Exception e) {
            SwgAuxiliar.msgExcepcion(e);
        }
    }

    private void muestraPrestamos() {
        try {
            desktopPane.add(Prestamos.listaPrestamos());
            desktopPane.selectFrame(false);
        } catch (Exception e) {
            SwgAuxiliar.msgExcepcion(e);
        }
    }

    private void nuevoPrestamo() throws Exception {
        PrestamosDTO p = new PrestamosDTO();
        desktopPane.add(Prestamos.fichaPrestamo(p));
        desktopPane.selectFrame(false);

    }

    /**
     * Método llamado al presionar la tecla 'Esc'. Muestra un diálogo para confirmar la salida.
     */
    private void salir() {
        if (JOptionPane.showConfirmDialog(FormMain.getInstance(), "¿Seguro que desea SALIR?", "Atención:", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            System.exit(0);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getComponent().equals(mArchivo)) miBarraDeEstado.setInfo("Opciones para archivos");
        else if (e.getComponent().equals(miAbrir)) miBarraDeEstado.setInfo("Pulsa para cargar una imagen en el visor");
        else if (e.getComponent().equals(miSalir)) miBarraDeEstado.setInfo("Cierra la aplicación");

        else
            miBarraDeEstado.setInfo(String.format("Infor: mArchivo: %b, miAbrir: %b, miAbrir: %b", mArchivo.isFocusable(), miAbrir.isFocusable(), miSalir.isFocusable()));
    }

    @Override
    public void focusLost(FocusEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        salir();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    public static int posInterna() {
        return FormMain.getInstance().getDesktopPane().getComponentCount() * 25; // hasta que no se visualiza no se contabiliza
    }

    public static void actualizaListaUsuarios() throws SQLException, CampoVacioExcepcion, IOException {
        List<UsuarioDTO> usuarios = Entidades.leerAllUsuarios();
        patron.actualizarListaUsuarios(usuarios);

    }

    public static void actualizaListaCategorias() throws SQLException, CampoVacioExcepcion, IOException {
        List<CategoriaDTO> categorias = Entidades.leerAllCategorias();
        patron.actualizarListaCategorias(categorias);

    }

    public static void actualizaListaLibros() throws SQLException, CampoVacioExcepcion, IOException {
        List<LibroDTO> libros = Entidades.leerAllLibros();
        patron.actualizarListaLibros(libros);

    }

    public static void actualizaListaPrestamos() throws SQLException, CampoVacioExcepcion, IOException {
        List<PrestamosDTO> prestamos = Entidades.leerAllPrestamos();
        patron.actualizarListaPrestamos(prestamos);

    }

    public static void barraEstado(String mensaje) {
        FormMain.getInstance().miBarraDeEstado.setInfo(mensaje);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) salir();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(miSalir)) salir();
        /*else if (e.getSource().equals(miConexion))
            loginPassword();*/
        else if (e.getSource() == miListaUsuarios) muestraUsuarios();
        else if (e.getSource() == miNuevoUsuario) nuevoUsuario();
        else if (e.getSource() == exportarXlsxUsuario) ExportadorXLSX.ExportarXLSXUsuarios();
        else if (e.getSource() == exportarXlsxLibro) ExportadorXLSX.ExportarXLSXLibros();
        else if (e.getSource() == exportarXlsxPrestamo) ExportadorXLSX.ExportarXLSXPrestamos();
        else if (e.getSource() == exportarXlsxCategoria) ExportadorXLSX.ExportarXLSXCategorias();

        else if (e.getSource() == miListaCategorias) muestraCategorias();
        else if (e.getSource() == miNuevaCategoria) nuevaCategoria();
        else if (e.getSource() == miListaLibros) muestraLibros();
        else if (e.getSource() == miNuevoLibro) nuevoLibro();

        else if (e.getSource() == miListaPrestamos) muestraPrestamos();
        else if (e.getSource() == miNuevoPrestamo) {
            try {
                nuevoPrestamo();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (e.getSource() == miGuardarLibro) {
            try {
                ExportadorCSV.exportarLibrosCSV();

            } catch (Exception ex) {
                SwgAuxiliar.msgExcepcion(ex);
            }
        }
    }
}
