package boj_16236;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static int[][] sea;
	static Shark shark;
	static int[] dx = {0, -1, 1, 0};
	static int[] dy = {-1, 0, 0, 1};
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		try {
			//�Է�
			N = Integer.parseInt(br.readLine());
			sea = new int[N][N];
			for(int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j = 0; j < N; j++) {
					int k = Integer.parseInt(st.nextToken());
					if(k == 9) {
						shark = new Shark(i, j);
					}
					else {
						sea[i][j] = k;
					}
				}
			}
			
			int time = 0;
			int t = 0;
			while((t = go(shark.i, shark.j)) != 0) {
				time += t;
			}
			
			System.out.println(time);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int go(int si, int sj) {
		Queue<Integer> queue = new LinkedList<Integer>();
		Fish target = new Fish(N, N, 0, N * N);
		int[][] check = new int[N][N];
		//���� ��ġ queue �߰�
		int cur_i = si;
		int cur_j = sj;
		check[cur_i][cur_j] = 1;
		queue.add(cur_i);
		queue.add(cur_j);
		//bfs����
		while(!queue.isEmpty()) {
			cur_i = queue.remove();
			cur_j = queue.remove();
			//ã�Ƶξ��� ����⺸�� �� ��� -> �׸��д�.
			if(check[cur_i][cur_j] > target.d)
				continue;
			//���� �� �ִ� ����⸦ ã�� ���
			if(sea[cur_i][cur_j] != 0 && shark.size > sea[cur_i][cur_j]) {
				if(cur_i < target.i) { //���� ������ ��ǥ���� �����Ѵ�.
					target = new Fish(cur_i, cur_j, sea[cur_i][cur_j], check[cur_i][cur_j]);
				}
				else if(cur_i == target.i && cur_j < target.j) { //�Ȱ��� ���� ���� ��, ���ʿ� ������ ��ǥ���� �����Ѵ�.
					target = new Fish(cur_i, cur_j, sea[cur_i][cur_j], check[cur_i][cur_j]);
				}
				continue;
			}
			//����⸦ ã�� ���� ��� ���� ĭ �̵�
			for(int i = 0; i < 4; i++) {
				int next_i = cur_i + dy[i];
				int next_j = cur_j + dx[i];
				//������ �Ѿ ��� -> �׸��д�.
				if(next_i < 0 || next_i >= N || next_j < 0 || next_j >= N)
					continue;
				//�̹� �Դ� ���� ��� -> �׸��д�.
				if(check[next_i][next_j] != 0)
					continue;
				//����� ũ�⺸�� ����Ⱑ ū ��� -> ������ �� ����.
				if(sea[next_i][next_j] > shark.size)
					continue;
				//���� ��ġ queue �߰�
				queue.add(next_i);
				queue.add(next_j);
				//�� ĭ �����Դ��� check�迭�� ����
				check[next_i][next_j] = check[cur_i][cur_j] + 1;
			}
		}
		//i�� N�� ���� �ʱ� ���� �״�� �����ִ� ��� ���̴� -> ���� �� �ִ� ����Ⱑ ����.
		if(target.i == N) {
			return 0;
		}
		//��ǥ���� ������ ����⸦ �Դ´�.
		shark.eat(target.size);
		shark.move(target.i, target.j);
		sea[target.i][target.j] = 0;
		//�ɸ� �ð�
		return target.d - 1;
	}
}

class Fish {
	int i;
	int j;
	int size;
	int d;
	Fish(int i, int j, int size, int d){
		this.i = i;
		this.j = j;
		this.size = size;
		this.d = d; //���κ����� �Ÿ�
	}
}

class Shark {
	int i;
	int j;
	int size = 2;
	int cnt = 0;
	Shark(int i, int j){
		this.i = i;
		this.j = j;
	}
	public void eat(int s) {
		cnt++;
		if(cnt == size) {
			size++;
			cnt = 0;
		}
	}
	public void move(int i, int j) {
		this.i = i;
		this.j = j;
	}
}