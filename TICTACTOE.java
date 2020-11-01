import java.util.Arrays;
import java.util.Scanner;
public class TICTACTOE {
	
	static int[] cache = new int[19683];
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
	
		int c = sc.nextInt();
		int oc, xc;
		
		char[][] board = new char[3][3];
		char turn;
		char elseturn;
		
		for(int i = 0; i < c; i++)
		{
			oc = 0; xc = 0;
			for(int j = 0; j < 3; j++)
			{
				String s = sc.next();
				for(int k = 0; k < 3; k++)
				{
					board[j][k] = s.charAt(k);
					if(board[j][k] == 'o') oc++;
					else if(board[j][k] == 'x') xc++;
				}
			}
			if(oc == xc) 
			{
				turn = 'x';
				elseturn = 'o';
			}
			else 
			{
				turn = 'o';
				elseturn = 'x';
			}
			
			Arrays.fill(cache, -2);
			
			int ret;
			
			ret = canWin(board,turn);
			
			if(ret == 0) System.out.println("TIE");
			else if(ret == -1) System.out.println(elseturn);
			else System.out.println(turn);
			
		}
		
		sc.close();
	}
	
	public static boolean isFinished(char[][] board, char turn)
	{
		int i, j;
		
		for(i = 0; i < 3; i++)
		{
			for(j = 0; j < 3; j++)
			{
				if(board[i][j] != turn) break;
			}
			if(j == 3) return true; 
		}
		
		for(i = 0; i < 3; i++)
		{
			for(j = 0; j < 3; j++)
			{
				if(board[j][i] != turn) break;
			}
			if(j == 3) return true; 
		}
		
		for(i = 0; i < 3; i++)
		{
			if(board[i][i] != turn) break;
		}
		if(i == 3) return true;
		
		for(i = 0; i < 3; i++)
		{
			if(board[i][2-i] != turn) break;
		}
		if(i == 3) return true;
		
		return false;
		
	}
	
	public static int canWin(char[][] board, char turn)
	{
		if(isFinished(board, (char)('o' + 'x' - turn))) return -1;
		int bj = bijection(board);
		if(cache[bj] != -2) return cache[bj];
		int minValue = 2;
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 3; x++)
			{
				if(board[y][x] == '.')
				{
					board[y][x] = turn;
					minValue = Math.min(minValue, canWin(board,(char)('o'+'x'-turn)));
					board[y][x] = '.';
					
				}
			}
		}
		
		if(minValue == 2 || minValue == 0) 
		{
			cache[bj] = 0;
			return cache[bj];
		}
		
		cache[bj] = -minValue;
		
		return cache[bj];		
	}
	
	public static int bijection(char[][] board)
	{
		int ret = 0;
		
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 3; x++)
			{
				ret *= 3;
				if(board[y][x] == 'o') ret += 1;
				else if(board[y][x] == 'x') ret += 2;
			}
		}
		
		return ret;
	}

}
