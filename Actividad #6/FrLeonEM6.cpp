#include <iostream>
#include <cstdlib>
#include <locale.h>
#include <iomanip>

using namespace std;

int main()
{
	setlocale(LC_ALL, "Spanish");
	
	float prom, nt;
	string nom, ape, cd;
	int c1, c2;
		
	for (c1 = 1; c1 <= 10; c1++)
	{
		cout << setw(43) << setfill('=') << "" << endl;
   		cout << setfill(' ') << setw(13) << " " << "Estudiante N°" << c1 << setw(20) << " " << endl;
		cout << setw(43) << setfill('=') << "" << endl;
		
		cout << "\nDigite su Cédula: ";
		cin >> cd;
		
		cout << "\nDigite su Nombre: ";
		cin >> nom;
		
		cout << "\nDigite su Apellido: ";
		cin >> ape;

		for (c2 = 1; c2 <= 8; c2++)
		{	
			cout << "\nDigite su Nota N°" << c2 << ": ";
			cin >> nt;
			
			while (!(nt >= 0 && nt <= 5))
			{
				c2 - 1;
				cout << "\nVuelva a digitar su nota N°" << c2;
				cout << "\nDigite su Nota N°" << c2 << ": ";
				cin >> nt;
			}
							
			prom += nt;
		}
		
		prom = prom / 8;
		
		system("cls");
		cout << "\n\n";
    	cout << "\t\t\t\t" << setw(43) << setfill('=') << "" << endl;
   		cout << "\t\t\t\t" << setfill(' ') << setw(13) << " " << "Estudiante N°" << c1 << setw(20) << " " << endl;
    	cout << "\t\t\t\t" << setw(43) << setfill('=') << "" << endl;
    	cout << "\n\n";
    	cout << "\t\t\t\t" << setfill(' ') << setw(30) << "Estudiante con la Cédula: " << cd << endl;
    	cout << "\t\t\t\t" << setw(30) << "Su Nota Final es de: " << fixed << setprecision(2) << prom << endl;
    	cout << "\n\n";

    	if (prom >= 3)
        	cout << "\t\t\t\t" << setw(43) << "Felicidades!, Usted Aprobo la Materia." << endl;
    	else
        	cout << "\t\t\t\t" << setw(43) << "Lamentablemente, Usted Reprobo la Materia." << endl;

    	cout << "\t\t\t\t" << setw(43) << setfill('=') << "" << endl;
    	cout << endl;
    	system("pause");
    	system("cls");
    	prom = 0;
	}
	system("pause");
    return 0;
}
