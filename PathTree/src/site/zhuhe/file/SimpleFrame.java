package site.zhuhe.file;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;

public class SimpleFrame extends JFrame {

    private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("我的电脑");
    private DefaultTreeModel model = new DefaultTreeModel(rootNode);
    private JTree tree = new JTree(model);

    public SimpleFrame(){
        model.setAsksAllowsChildren(true);
        tree.addTreeSelectionListener(e -> {
            TreePath path = e.getPath();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
                    .getLastPathComponent();

            Object userObject = node.getUserObject();
            if (!(userObject instanceof FileNode)) {
                return;
            }
            FileNode folder = (FileNode) userObject;
            if (!folder.isDirectory()) {
                return;
            }
            FileNode[] files = folder.listFiles();
            for (FileNode file : files) {
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(file);
                if (file.isDirectory()) {
                    child.setAllowsChildren(true);
                }
                model.insertNodeInto(child, node, node.getChildCount());
            }
        });

        FileNode[] files = FileNode.listRoots();
        for(FileNode file : files){
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(file);
            if (file.isDirectory()) {
                node.setAllowsChildren(true);
            }
            rootNode.add(node);
        }
        tree.expandPath(new TreePath(rootNode));

        init();
    }

    private void init() {
        JPanel panel = new JPanel();
        JTextArea area = new JTextArea(5, 30);
        JScrollPane js1 = new JScrollPane(tree,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel.setLayout(new FlowLayout());
        JScrollPane js = new JScrollPane(area);
        JSplitPane jsplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
                js1, js);
        panel.add(jsplit);
        setContentPane(panel);
        setSize(600, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
