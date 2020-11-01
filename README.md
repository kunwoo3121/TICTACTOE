# TICTACTOE

https://algospot.com/judge/problem/read/TICTACTOE

# 구현 방법
```
 i)   놓여 있는 x와 o의 개수를 통해 누구의 차례인지 확인한다.
  
 ii)  이제 모든 경우를 재귀함수를 통해 체크한다. 빈 자리에 차례인 사람의 돌을 놓고 게임이 끝났는지 체크한다.
 
 iii) 모든 경우를 체크해보았지만 게임이 끝나지 않았을 때는 무승부가 된다.
 
 iv)  중복되는 연산을 제거하기 위해서 일차원 cache 배열을 이용한다.
 
 v)   각 칸을 o가 놓여있을 때를 1 x가 놓여있을 때를 2, 아무것도 놓여있지 않을 때를 0이라고 생각하고 일차원 cache배열에 이미 계산했던 게임판의 결과를 저장한다.
 
 vi)  예를 들어 xx. 와 같이 게임판이 놓여있을 때 이것의 계산 결과는 2 * 3^8 + 2 * 3^7 + 0 * 3^6 + 1 * 3^5 + 1 * 3^4 + 0 * 3^3 + 0 * 3^2 + 0 * 3^1 + 0 * 1 = 17820
               oo.            
               ...
      따라서 cache[17820]에 저장이 될 것이다.
      
 vii) cache 배열을 이용하여 중복되는 계산을 없애며 모든 경우의 수를 체크해보면 답이 나온다.
```

# 구현 코드
```java
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
```
