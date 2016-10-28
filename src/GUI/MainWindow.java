package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by VIKAS RAJPUT on 28-10-2016.
 */
public class MainWindow
{
    JFrame mainFrame;
    BoardPanel mainBoardPanel;


    Dimension windowDimension = new Dimension(1000, 1000);
    Dimension boardDimesnion = new Dimension(1000, 1000);
    Dimension tileDimension = new Dimension(1000/10, 1000/10);


    public MainWindow()
    {
        JFrame.setDefaultLookAndFeelDecorated(true);
        mainFrame = new JFrame("SNAKES AND LADDER");
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = createFileMenu();
        menuBar.add(createOptions());
        mainFrame.setJMenuBar(menuBar);

        mainBoardPanel = new BoardPanel();
        mainFrame.add(mainBoardPanel, BorderLayout.CENTER);

        mainFrame.setSize(windowDimension);
        mainFrame.setVisible(true);
    }

    private JMenuBar createOptions()
    {
        JMenuBar optionBar = new JMenuBar();

        JMenu option = new JMenu("Options");

        JMenuItem option1 = new JMenuItem("Option 1");
        option1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

            }
        });
        option.add(option1);

        optionBar.add(option);
        return optionBar;
    }

    private JMenuBar createFileMenu()
    {
        JMenuBar fileBar = new JMenuBar();

        JMenu file = new JMenu("File");

        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        file.add(quit);

        fileBar.add(file);
        return fileBar;
    }

    class BoardPanel extends JPanel
    {
        TilePanel[] boardTiles;

        BoardPanel()
        {
            super(new GridLayout(10, 10));
            boardTiles = new TilePanel[100];

            for(int i = 0; i < 100; i++)
            {
                boardTiles[i] = new TilePanel(this, i);
                add(boardTiles[i]);
            }

            setPreferredSize(boardDimesnion);
            validate();
        }
    }

    class TilePanel extends JPanel
    {
        int tileIndex;

        TilePanel(BoardPanel boardPanel, int tileIndex)
        {
            super(new GridBagLayout());
            this.tileIndex = tileIndex;
            setPreferredSize(tileDimension);
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            tileColor();
            printTileID();

            validate();
        }

        private void printTileID()
        {
            JLabel indexLabel = new JLabel(String.valueOf(tileIndex));
            indexLabel.setFont(new Font("Raleway", Font.BOLD, 26));
            indexLabel.setPreferredSize(new Dimension(90, 90));
            indexLabel.setHorizontalAlignment(JLabel.RIGHT);
            indexLabel.setVerticalAlignment(JLabel.BOTTOM);

            add(indexLabel);
        }

        private void tileColor()
        {
            switch (tileIndex % 7)
            {
                case 0:
                    setBackground(new Color(255, 141, 240));
                    break;
                case 1:
                    setBackground(new Color(255, 149, 168));
                    break;
                case 2:
                setBackground(new Color(186, 205, 255));
                    break;
                case 3:
                    setBackground(new Color(152, 255, 156));
                    break;
                case 4:
                    setBackground(new Color(255, 246, 133));
                    break;
                case 5:
                    setBackground(new Color(255, 178, 126));
                    break;
                case 6:
                    setBackground(new Color(255, 122, 137));
                    break;
            }
        }
    }
}
