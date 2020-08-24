/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controller.ArticulosJpaController;
import controller.ClientesJpaController;
import controller.FacturaarticuloJpaController;
import controller.FacturasJpaController;
import entities.Articulos;
import entities.Clientes;
import static entities.Clientes_.correo;
import entities.Facturaarticulo;
import entities.Facturas;
import java.awt.Button;
import java.awt.event.WindowEvent;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SelecciónArticulos extends javax.swing.JFrame {

    ArticulosJpaController CArticulos = new ArticulosJpaController();
    Articulos EArticulos = new Articulos();
    
    ClientesJpaController CClientes = new ClientesJpaController();
    Clientes EClientes = new Clientes();
    
    FacturasJpaController CFacturas = new FacturasJpaController();
    
    FacturaarticuloJpaController CFacturarticulo = new FacturaarticuloJpaController();
    List rs;
    
    public int idcli;

    DefaultTableModel modelo;
    private String correo;

    public SelecciónArticulos() {
        initComponents();
        listar_articulos();
        calcular_total();
    }

    public SelecciónArticulos(String nombres, String apellidos, String identificacion, String correo) {
        initComponents();
        listar_articulos();
        calcular_total();
        txtNombres.setText(nombres);
        txtApellidos.setText(apellidos);
        txtIdentificacion.setText(identificacion);
        this.correo = correo;
               
                
    }

    void listar_articulos() {

        try {
            modelo = (DefaultTableModel) tablaArticulos.getModel();
            rs = CArticulos.findArticulosEntities();

            Object[] articulo = new Object[5];

            for (int i=0; i<rs.size(); i++) {
                EArticulos = (Articulos)rs.get(i);
                articulo[0] = EArticulos.getIdArt();
                articulo[1] = EArticulos.getCodigo();
                articulo[2] = EArticulos.getNombre();
                articulo[3] = EArticulos.getDescripcion();
                articulo[4] = EArticulos.getPrecio();



                modelo.addRow(articulo);
            }

            tablaArticulos.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos");
        }
    }

    void update_cliente() {

         idcli = CClientes.ultimoId();
         
     
        String nombres = txtNombres.getText();
        String apellidos = txtApellidos.getText();
        String identificaion = txtIdentificacion.getText();
        String telefono = txtTelefono.getText();
        String ciudad = txtCiudad.getText();
        String direccion = txtDireccion.getText();
   
        if (identificaion.equals("") || telefono.equals("") || ciudad.equals("") || direccion.equals("")) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
        } else {

            try {
                Clientes clientes = new Clientes(idcli, nombres,  apellidos,  identificaion,  correo,  telefono,  ciudad,  direccion);
                CClientes.edit(clientes);

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    void hacer_compra() {

        LocalDateTime fechaHora = LocalDateTime.now();
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String Fecha_Hora = fechaHora.format(formater);

        //int idCli = 0;
        String identificacion = txtIdentificacion.getText();

        String total = txtTotal.getText();

        ArrayList<List<String>> articulos = new ArrayList<>();

        String data = null;

        for (int row = 0; row < tablaSeleccion.getRowCount(); row++) {
            List<String> articulo = new ArrayList<>();

            String idArt = (String) tablaSeleccion.getValueAt(row, 0).toString();
            String cantidad = (String) tablaSeleccion.getValueAt(row, 5).toString();

            articulo.add(idArt);
            articulo.add(cantidad);

            articulos.add(articulo);
        }

        String identificaion = txtIdentificacion.getText();
        String telefono = txtTelefono.getText();
        String ciudad = txtCiudad.getText();
        String direccion = txtDireccion.getText();
        if (identificaion.equals("") || telefono.equals("") || ciudad.equals("") || direccion.equals("")) {

            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos de envío");

        } else {

            try {                    
                
                Facturas factura = new Facturas(null, Fecha_Hora, idcli,  total);
                CFacturas.create(factura);
                
                for (int i = 0; i < articulos.size(); i++) {

                    int idArt = Integer.parseInt(articulos.get(i).get(0));
                    int cantidad = Integer.parseInt(articulos.get(i).get(1));
                    
                    Facturaarticulo facturaarticulo = new Facturaarticulo(null, idcli, idArt, cantidad);
                    CFacturarticulo.create(facturaarticulo);
                }
                    
            } catch (Exception e) {

            }
        }
    }

    public ArrayList<List<String>> enviar_datos() {

        ArrayList<List<String>> articulos = new ArrayList<>();
        String data = null;

        for (int row = 0; row < tablaSeleccion.getRowCount(); row++) {
            List<String> articulo = new ArrayList<>();

            for (int col = 0; col < tablaSeleccion.getColumnCount(); col++) {

                data = (String) tablaSeleccion.getValueAt(row, col).toString();
                articulo.add(data);

                if (col == 5) {
                    break;
                }
            }

            articulos.add(articulo);
        }

        return articulos;
    }

    int calcular_total() {

        int total = 0;
        float precio = 0;
        int cantidad = 0;
        int subtotal = 0;
        int i = 0;

        if (tablaSeleccion.getRowCount() == 0) {

            txtTotal.setText("0");

        } else {

            try {

                while (i < tablaSeleccion.getRowCount()) {

                    precio = (float) tablaSeleccion.getValueAt(i, 4);
                    cantidad = (int) tablaSeleccion.getValueAt(i, 5);

                    subtotal += (int) (precio * cantidad);

                    i++;
                }

                total = subtotal;
                txtTotal.setText(Integer.toString(total));

            } catch (Exception e) {

                System.out.println(e);
            }

        }

        return total;
    }

    void limpiar_tabla() {
        for (int i = 0; i < tablaSeleccion.getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }
    }

    void salir() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaArticulos = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaSeleccion = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtCiudad = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        btnSalir = new javax.swing.JButton();
        btnPagar = new javax.swing.JButton();
        txtNombres = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtIdentificacion = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btnLimpiar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Raleway Black", 0, 36)); // NOI18N
        jLabel1.setText("PAPELERIA BOBADITAS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(226, 226, 226)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(40, 40, 40))
        );

        jLabel2.setFont(new java.awt.Font("Raleway SemiBold", 1, 18)); // NOI18N
        jLabel2.setText("SELECCIONA NUESTROS PRODUCTOS DISPONIBLES");

        tablaArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CÓDIGO", "NOMBRE", "DESCRIPCIÓN", "PRECIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaArticulos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaArticulosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaArticulos);

        jLabel3.setFont(new java.awt.Font("Raleway SemiBold", 1, 15)); // NOI18N
        jLabel3.setText("PRODUCTOS SELECCIONADOS");

        tablaSeleccion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CÓDIGO", "NOMBRE", "DESCRIPCIÓN", "PRECIO", "CANTIDAD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaSeleccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaSeleccionMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaSeleccion);

        jLabel4.setFont(new java.awt.Font("Raleway Black", 0, 15)); // NOI18N
        jLabel4.setText("VALOR A PAGAR:");

        jLabel5.setFont(new java.awt.Font("Raleway SemiBold", 0, 15)); // NOI18N
        jLabel5.setText("$");

        txtTotal.setFont(new java.awt.Font("Raleway SemiBold", 0, 15)); // NOI18N
        txtTotal.setText("0.0");

        jLabel6.setFont(new java.awt.Font("Raleway SemiBold", 1, 18)); // NOI18N
        jLabel6.setText("DATOS DE DOMICILIO");

        jLabel14.setFont(new java.awt.Font("Raleway Medium", 0, 15)); // NOI18N
        jLabel14.setText("CIUDAD:");

        jLabel15.setFont(new java.awt.Font("Raleway Medium", 0, 15)); // NOI18N
        jLabel15.setText("DIRECCIÓN:");

        jLabel16.setFont(new java.awt.Font("Raleway Medium", 0, 15)); // NOI18N
        jLabel16.setText("TELÉFONO:");

        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnPagar.setText("PAGAR");
        btnPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagarActionPerformed(evt);
            }
        });

        txtNombres.setFont(new java.awt.Font("Raleway Medium", 0, 15)); // NOI18N
        txtNombres.setText("txtNombres");

        jLabel10.setFont(new java.awt.Font("Raleway Medium", 0, 15)); // NOI18N
        jLabel10.setText("NOMBRE DEL CLIENTE :");

        txtIdentificacion.setFont(new java.awt.Font("Raleway Medium", 0, 15)); // NOI18N
        txtIdentificacion.setText("txtIdentificacion");

        txtApellidos.setFont(new java.awt.Font("Raleway Medium", 0, 15)); // NOI18N
        txtApellidos.setText("txtApellidos");

        jLabel18.setFont(new java.awt.Font("Raleway Medium", 0, 15)); // NOI18N
        jLabel18.setText("IDENTIFICACION: ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jLabel6))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(txtNombres)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtApellidos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel18)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdentificacion))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(32, 32, 32)
                                .addComponent(txtCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPagar, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtNombres)
                    .addComponent(txtApellidos)
                    .addComponent(jLabel18)
                    .addComponent(txtIdentificacion))
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(76, 76, 76)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir)
                    .addComponent(btnPagar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnLimpiar.setText("LIMPIAR LISTA");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotal)
                        .addGap(84, 84, 84))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(txtTotal)
                    .addComponent(btnLimpiar))
                .addGap(26, 26, 26)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaArticulosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaArticulosMouseClicked
        // TODO add your handling code here:
        int fila = tablaArticulos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Articulo no seleccionado");

        } else {

            int cantidad = Integer.parseInt(JOptionPane.showInputDialog("¿Qué Cantidad Desea?"));

            int idArt = Integer.parseInt((String) tablaArticulos.getValueAt(fila, 0).toString());
            String codigo = (String) tablaArticulos.getValueAt(fila, 1);
            String nombre = (String) tablaArticulos.getValueAt(fila, 2);
            String descripcion = (String) tablaArticulos.getValueAt(fila, 3);
            float precio = Float.parseFloat((String) tablaArticulos.getValueAt(fila, 4).toString());

            Object[] select = new Object[6];
            select[0] = idArt;
            select[1] = codigo;
            select[2] = nombre;
            select[3] = descripcion;
            select[4] = precio;
            select[5] = cantidad;

            modelo = (DefaultTableModel) tablaSeleccion.getModel();
            modelo.addRow(select);
            tablaSeleccion.setModel(modelo);

            calcular_total();
        }
    }//GEN-LAST:event_tablaArticulosMouseClicked

    private void tablaSeleccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaSeleccionMouseClicked
        // TODO add your handling code here:

        int fila = tablaSeleccion.getSelectedRow();
        modelo.removeRow(fila);
        calcular_total();
    }//GEN-LAST:event_tablaSeleccionMouseClicked

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:

        salir();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        txtTotal.setText("0");
        limpiar_tabla();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarActionPerformed
        // TODO add your handling code here:

        update_cliente();
        hacer_compra();

        ArrayList<List<String>> articulos = enviar_datos();
        String identificacion = txtIdentificacion.getText();
        String total = txtTotal.getText();

        FacturaFinal factura = new FacturaFinal(articulos, identificacion, total);

        try {
            Thread.sleep(2000);

            factura.setVisible(true);
            this.setVisible(false);

        } catch (InterruptedException e) {
        }

    }//GEN-LAST:event_btnPagarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SelecciónArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SelecciónArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SelecciónArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SelecciónArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SelecciónArticulos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnPagar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaArticulos;
    private javax.swing.JTable tablaSeleccion;
    private javax.swing.JLabel txtApellidos;
    private javax.swing.JTextField txtCiudad;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JLabel txtIdentificacion;
    private javax.swing.JLabel txtNombres;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JLabel txtTotal;
    // End of variables declaration//GEN-END:variables
}
