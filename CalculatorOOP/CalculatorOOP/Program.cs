using System;

namespace CalculatorOOP
{

    interface IAdvanced
    {
        double Log10();
    }


    abstract class AbstractCalc
    {
        public abstract double Add();
        public abstract double Sub();
        public abstract double Mul();
        public abstract double Div();

    }

    class OrdinaryCalc : AbstractCalc
    {

        public double num1 { get; set; }
        public double num2 { get; set; }


        public OrdinaryCalc()
        {
            Console.WriteLine("=");
        }
        public override double Add()
        {
            return num1 + num2;
        }
        public override double Sub()
        {
            return num1 - num2;
        }
        public override double Mul()
        {
            return num1 * num2;
        }
        public override double Div()
        {
            return num1 / num2;
        }
    }
    class AdvancedCalc : OrdinaryCalc, IAdvanced
    {

        public AdvancedCalc()
        {

        }
        public double Log10()
        {
            if (num1 > 0) return Math.Log10(num1);
            else return 0;
        }
    }


    class Program
    {
        static void Main(string[] args)
        {
            int s = 0;

            void EnterData()
            {
                string num1, z; double num2, num11;
                Console.WriteLine("введите арифметическое выражение: ");
                if (s == 2)
                {
                    num1 = Console.ReadLine();
                    if (num1 == "lg")
                    {
                        double l;
                        l = double.Parse(Console.ReadLine());
                        AdvancedCalc obj1 = new AdvancedCalc();
                        obj1.num1 = l;
                        Console.WriteLine(obj1.Log10());
                    }
                    else
                    {
                        num11 = double.Parse(num1);
                        z = Console.ReadLine();
                        num2 = double.Parse(Console.ReadLine());
                        AdvancedCalc obj = new AdvancedCalc();
                        obj.num1 = num11;
                        obj.num2 = num2;
                        switch (z)
                        {
                            case "+":
                                Console.WriteLine(obj.Add());
                                break;
                            case "-":
                                Console.WriteLine(obj.Sub());
                                break;
                            case "*":
                                Console.WriteLine(obj.Mul());
                                break;
                            case "/":
                                if (num2 == 0) Console.WriteLine("Ошибка");
                                else Console.WriteLine(obj.Div());
                                break;
                            default:
                                break;
                        }
                    }
                }
                else if (s == 1)
                {
                    num11 = double.Parse(Console.ReadLine());
                    z = Console.ReadLine();
                    num2 = double.Parse(Console.ReadLine());
                    OrdinaryCalc obj2 = new OrdinaryCalc();
                    obj2.num1 = num11;
                    obj2.num2 = num2;
                    switch (z)
                    {
                        case "+":
                            Console.WriteLine(obj2.Add());
                            break;
                        case "-":
                            Console.WriteLine(obj2.Sub());
                            break;
                        case "*":
                            Console.WriteLine(obj2.Mul());
                            break;
                        case "/":
                            if (num2 == 0) Console.WriteLine("Ошибка");
                            else Console.WriteLine(obj2.Div());
                            break;
                        default:
                            break;
                    }
                }
            }

            void Menu()
            {
                Console.WriteLine("\tКалькулятор");
                Console.WriteLine("1 - обычный калькулятор");
                Console.WriteLine("2 - инженерный калькулятор");
                Console.WriteLine("3 - выход из программы\n");

                s = int.Parse(Console.ReadLine());
                switch (s)
                {
                    case 1:
                        EnterData();
                        Menu();
                        break;
                    case 2:
                        EnterData();
                        Menu();
                        break;
                    case 3:
                        break;
                    default: break;
                }
            }

            Menu();
        }
    }

}
