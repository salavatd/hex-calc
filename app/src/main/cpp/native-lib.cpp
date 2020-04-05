#include <jni.h>
#include <string>

std::string decToBin(const int &decNumber) {
    int number = decNumber;
    std::string binString;
    if (decNumber == 0)
        return "0";
    else if (decNumber < 0)
        number *= -1;
    while (number > 0) {
        binString.insert(0, 1, char('0' + number % 2));
        number = signed(unsigned(number) >> 1u);
    }
    if (decNumber < 0)
        binString.insert(0, 1, '-');
    return binString;
}

std::string decToOct(const int &decNumber) {
    int number = decNumber;
    std::string octString;
    if (decNumber == 0)
        return "0";
    else if (decNumber < 0)
        number *= -1;
    while (number > 0) {
        octString.insert(0, 1, char('0' + number % 8));
        number = signed(unsigned(number) >> 3u);
    }
    if (decNumber < 0)
        octString.insert(0, 1, '-');
    return octString;
}

std::string decToHex(const int &decNumber) {
    int number = decNumber;
    std::string hexString;
    char digitChar;
    if (decNumber == 0)
        return "0";
    else if (decNumber < 0)
        number *= -1;
    while (number > 0) {
        switch (number % 16) {
            case 10:
                digitChar = 'A';
                break;
            case 11:
                digitChar = 'B';
                break;
            case 12:
                digitChar = 'C';
                break;
            case 13:
                digitChar = 'D';
                break;
            case 14:
                digitChar = 'E';
                break;
            case 15:
                digitChar = 'F';
                break;
            default:
                digitChar = char('0' + number % 16);
        }
        hexString.insert(0, 1, digitChar);
        number = signed(unsigned(number) >> 4u);
    }
    if (decNumber < 0)
        hexString.insert(0, 1, '-');
    return hexString;
}

int binToDec(std::string binString) {
    int sign = 1;
    int decNumber = 0;
    if (binString[0] == '-') {
        binString.erase(0, 1);
        sign *= -1;
    }
    unsigned int binStringLength = binString.length();
    for (char i : binString) {
        binStringLength--;
        if (i - '0' == 1) {
            decNumber += signed(1u << binStringLength);
        }
    }
    return decNumber * sign;
}

int octToDec(std::string octString) {
    int sign = 1;
    int decNumber = 0;
    if (octString[0] == '-') {
        octString.erase(0, 1);
        sign *= -1;
    }
    unsigned int octStringLength = octString.length();
    for (char i : octString) {
        octStringLength--;
        decNumber += (i - '0') * signed(1u << 3 * octStringLength);
    }
    return decNumber * sign;
}

int hexToDec(std::string hexString) {
    int sign = 1;
    int decNumber = 0;
    int digit;
    if (hexString[0] == '-') {
        hexString.erase(0, 1);
        sign *= -1;
    }
    unsigned int hexStringLength = hexString.length();
    for (char i : hexString) {
        hexStringLength--;
        switch (i) {
            case 'A':
                digit = 10;
                break;
            case 'B':
                digit = 11;
                break;
            case 'C':
                digit = 12;
                break;
            case 'D':
                digit = 13;
                break;
            case 'E':
                digit = 14;
                break;
            case 'F':
                digit = 15;
                break;
            default:
                digit = i - '0';
        }
        decNumber = decNumber + digit * signed(1u << 4 * hexStringLength);
    }
    return decNumber * sign;
}

void manager(int &decNumber, std::string &binString, std::string &octString, std::string &hexString,
             const int &numberSystemBase) {
    switch (numberSystemBase) {
        case 2:
            decNumber = binToDec(binString);
            octString = decToOct(decNumber);
            hexString = decToHex(decNumber);
            break;
        case 8:
            decNumber = octToDec(octString);
            binString = decToBin(decNumber);
            hexString = decToHex(decNumber);
            break;
        case 10:
            binString = decToBin(decNumber);
            octString = decToOct(decNumber);
            hexString = decToHex(decNumber);
            break;
        case 16:
            decNumber = hexToDec(hexString);
            binString = decToBin(decNumber);
            octString = decToOct(decNumber);
            break;
        default:;
    }
}

extern "C" JNIEXPORT jstring JNICALL
Java_com.salavatdautov_hexcalc_MainActivity_calculate(JNIEnv *env, jobject /* this */, jint operand1,
                                                jint operand2, jchar operation) {
    std::string result;
    switch (operation) {
        case '+':
            result = std::to_string(operand1 + operand2);
            break;
        case '-':
            result = std::to_string(operand1 - operand2);
            break;
        case '*':
            result = std::to_string(operand1 * operand2);
            break;
        case '/':
            result = std::to_string(operand1 / operand2);
        default:
            break;
    }
    return env->NewStringUTF(result.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com.salavatdautov_hexcalc_MainActivity_calculateNoDec(JNIEnv *env, jobject /* this */,
                                                     jstring operand1, jstring operand2,
                                                     jchar operation, jint numberSystemBase) {
    std::string result;
    int decNumberResult;
    jboolean isCopy;

    int decNumber1;
    std::string binString1;
    std::string octString1;
    std::string hexString1;
    size_t operandLength1 = (size_t) env->GetStringLength(operand1);
    const char *operandBytes1 = env->GetStringUTFChars(operand1, &isCopy);

    int decNumber2;
    std::string binString2;
    std::string octString2;
    std::string hexString2;
    size_t operandLength2 = (size_t) env->GetStringLength(operand2);
    const char *operandBytes2 = env->GetStringUTFChars(operand2, &isCopy);

    switch (numberSystemBase) {
        case 2:
            binString1 = std::string(operandBytes1, operandLength1);
            binString2 = std::string(operandBytes2, operandLength2);
            break;
        case 8:
            octString1 = std::string(operandBytes1, operandLength1);
            octString2 = std::string(operandBytes2, operandLength2);
            break;
        case 16:
            hexString1 = std::string(operandBytes1, operandLength1);
            hexString2 = std::string(operandBytes2, operandLength2);
    }

    manager(decNumber1, binString1, octString1, hexString1, numberSystemBase);
    manager(decNumber2, binString2, octString2, hexString2, numberSystemBase);

    switch (operation) {
        case '+':
            decNumberResult = decNumber1 + decNumber2;
            break;
        case '-':
            decNumberResult = decNumber1 - decNumber2;
            break;
        case '*':
            decNumberResult = decNumber1 * decNumber2;
            break;
        case '/':
            decNumberResult = decNumber1 / decNumber2;
    }

    switch (numberSystemBase) {
        case 2:
            result = decToBin(decNumberResult);
            break;
        case 8:
            result = decToOct(decNumberResult);
            break;
        case 16:
            result = decToHex(decNumberResult);
            break;
    }
    return env->NewStringUTF(result.c_str());
}