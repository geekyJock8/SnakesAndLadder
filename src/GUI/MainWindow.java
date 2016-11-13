package GUI;

import ENGINE.Board;
import ENGINE.Dice;
import ENGINE.Game;
import PLAYER.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.BorderLayout.*;
import static javax.swing.SwingConstants.*;
import static javax.swing.text.StyleConstants.setIcon;

/**
 * Created by VIKAS RAJPUT on 28-10-2016.
 */
public class MainWindow
{
    JFrame mainFrame;
    BoardPanel mainBoardPanel;
    DicePanel dicePanel;
    Game game;

    Dimension windowDimension = new Dimension(1280, 720);
    Dimension boardDimesnion = new Dimension(1000, 1000);
    Dimension tileDimension = new Dimension(100, 100);


    public MainWindow()
    {
        game = new Game();
        JFrame.setDefaultLookAndFeelDecorated(false);
        mainFrame = new JFrame("SNAKES AND LADDER");
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = createFileMenu();
        menuBar.add(createOptions());
        mainFrame.setJMenuBar(menuBar);

        mainBoardPanel = new BoardPanel();
        mainFrame.add(mainBoardPanel, BorderLayout.CENTER);

        dicePanel = new DicePanel();
        dicePanel.setBackground(Color.BLACK.brighter());
        dicePanel.setPreferredSize(new Dimension(150, 720));
        mainFrame.add(dicePanel, BorderLayout.EAST);

        mainFrame.setSize(windowDimension);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
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


    class DicePanel extends JPanel
    {
        JButton diceButton;
        JLabel diceValue;
        JLabel playerLabel;

        DicePanel()
        {
            super();
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            playerLabel = new JLabel(String.valueOf(game.p[game.getChance()].getName()));
            playerLabel.setFont(new Font("LATO", Font.PLAIN, 28));
            playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            playerLabel.setForeground(Color.WHITE.darker());

            diceButton = new JButton();

            JLabel diceLabel = new JLabel("Dice Roll");
            diceLabel.setFont(new Font("LATO", Font.PLAIN, 24));
            diceLabel.setForeground(Color.BLACK.darker());

            diceButton.add(diceLabel);

            diceButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            diceButton.setBackground(Color.WHITE.brighter());
            diceButton.setPreferredSize(new Dimension(110, 60));

            diceButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    game.makeMove();

                    diceValue.setText(String.valueOf(game.dice.getRollValue()));
                    playerLabel.setText(String.valueOf(game.p[game.getChance()].getName()));

                    SwingUtilities.invokeLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            mainBoardPanel.drawBoard(game);

                            if(game.isGameOver())
                            {
                                mainBoardPanel.gameOver();
                                gameOver();
                            }
                        }
                    });

                    if(game.dice.getRollValue() != 6)
                        game.nextChance();

                }
            });

            diceValue = new JLabel(String.valueOf(game.dice.getRollValue()));
            diceValue.setForeground(Color.WHITE);
            diceValue.setAlignmentX(Component.CENTER_ALIGNMENT);
            diceValue.setPreferredSize(new Dimension(110,80));
            diceValue.setFont(new Font("ROBOTO", Font.ITALIC, 28));

            add(playerLabel);
            add(Box.createRigidArea(new Dimension(10, 300)));
            add(diceButton);
            add(Box.createRigidArea(new Dimension(10, 300)));
            add(diceValue);
        }

        private void gameOver()
        {
            remove(diceButton);

            diceValue.setText("WON");


        }
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


        public void drawBoard(Game game)
        {
            removeAll();

            for(int i = 0; i < 100; i++)
            {
                boardTiles[i].drawTiles(game);
                add(boardTiles[i]);
            }

            revalidate();
            repaint();
        }

        public void gameOver()
        {
            removeAll();

            setBackground(new Color(0, 0, 0));
            setForeground(new Color(255, 255, 255).brighter());
            JLabel gameEndMessage = new JLabel("GAME OVER");
            gameEndMessage.setFont(new Font("Rage", Font.ITALIC, 60));
            gameEndMessage.setVerticalAlignment(SwingConstants.CENTER);
            gameEndMessage.setHorizontalAlignment(SwingConstants.CENTER);
            //gameEndMessage.setPreferredSize(new Dimension(200, 200));

            add(gameEndMessage, BorderLayout.CENTER);
        }
    }

    class TilePanel extends JPanel
    {
        int tileIndex;

        TilePanel(BoardPanel boardPanel, int tileIndex)
        {
            super(new BorderLayout());
            this.tileIndex = tileIndex;
            setPreferredSize(tileDimension);
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            tileColor();
            printTileID();

            if(game.board.isSnakePresent(tileIndex))
                drawSnakeHead();
            if(game.board.isLadderPresent(tileIndex))
                drawLadder();
            if(game.board.isLadderTopPresent(tileIndex))
                drawLadderTop();
            if(game.board.isSnakeTailPresent(tileIndex))
                drawSnakeTail();

            for(int i = 0; i < 2; i++)
            {
                if(game.p[i].getPlayerPos() == tileIndex)
                    drawPlayers(i);
            }

            validate();
        }

        private void drawSnakeHead()
        {
            BufferedImage img = null;
            try
            {
                img = ImageIO.read(new File("img/snake/front/1.gif"));
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            Image dimg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dimg);
            JLabel imgSnake = new JLabel(imageIcon);
            imgSnake.setHorizontalAlignment(SwingConstants.LEFT);
            imgSnake.setVerticalAlignment(TOP);
            add(imgSnake, BorderLayout.WEST);
        }

        private void drawLadderTop()
        {
            BufferedImage img = null;
            try
            {
                img = ImageIO.read(new File("img/ladder/top/1.gif"));
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            Image dimg = img.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dimg);
            JLabel imgLadderTop = new JLabel(imageIcon);
            imgLadderTop.setHorizontalAlignment(SwingConstants.LEFT);
            imgLadderTop.setVerticalAlignment(BOTTOM);
            add(imgLadderTop, BorderLayout.WEST);
        }

        private void drawLadder()
        {
            BufferedImage img = null;
            try
            {
                img = ImageIO.read(new File("img/ladder/bottom/1.gif"));
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            Image dimg = img.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dimg);
            JLabel imgLadder = new JLabel(imageIcon);
            imgLadder.setHorizontalAlignment(SwingConstants.LEFT);
            imgLadder.setVerticalAlignment(BOTTOM);
            add(imgLadder, BorderLayout.WEST);
        }

        private void drawSnakeTail()
        {
            BufferedImage img = null;
            try
            {
                img = ImageIO.read(new File("img/snake/back/1.gif"));
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            Image dimg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dimg);
            JLabel imgSnake = new JLabel(imageIcon);
            imgSnake.setHorizontalAlignment(SwingConstants.LEFT);
            imgSnake.setVerticalAlignment(BOTTOM);
            add(imgSnake, BorderLayout.WEST);
        }

        private void printTileID()
        {
            JLabel indexLabel = new JLabel(String.valueOf(tileIndex));
            indexLabel.setFont(new Font("Raleway", Font.BOLD, 25));
            indexLabel.setPreferredSize(new Dimension(75, 75));
            indexLabel.setVerticalAlignment(BOTTOM);
            indexLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            add(indexLabel, BorderLayout.EAST);
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

        public void drawTiles(Game game)
        {
            removeAll();
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            tileColor();
            printTileID();

            if(game.board.isSnakePresent(tileIndex))
               drawSnakeHead();
            if(game.board.isLadderPresent(tileIndex))
                drawLadder();
            if(game.board.isLadderTopPresent(tileIndex))
                drawLadderTop();
            if(game.board.isSnakeTailPresent(tileIndex))
                drawSnakeTail();

            for(int i = 0; i < 2; i++)
            {
                if(game.p[i].getPlayerPos() == tileIndex)
                    drawPlayers(i);
            }

            revalidate();
            repaint();
        }

        private void drawPlayers(int i)
        {
            BufferedImage img = null;
            try
            {
                img = ImageIO.read(new File("img/player/" + i + ".gif"));
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            ImageIcon imageIcon = new ImageIcon(img);
            JLabel imgPlayer = new JLabel(imageIcon);
            if(i == 0)
                add(imgPlayer, BorderLayout.NORTH);
            else
                add(imgPlayer, BorderLayout.SOUTH);
        }
    }
}
