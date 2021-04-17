#include <jni.h>
#include <string> // C++
#include <string.h> // C
#include <stdlib.h>
#include <stdio.h>

extern "C" JNIEXPORT jstring JNICALL Java_com_setsunajin_asisten_MainActivity_stringFromJNI(JNIEnv* env, jobject /* this */)
{
    std::string hello = "Hello zz from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" jstring Java_com_setsunajin_asisten_MainBrowser_filefbtes( JNIEnv* env, jobject, jstring inku)
{
    std::string hello = "asasasasa";
    const char* path = env->GetStringUTFChars(inku, NULL);

    if (NULL == path)
        path = "kosong";

    return env->NewStringUTF(path);
}

void getBase64(const char *path, const char *nama)
{
    printf("sdsd\n");
    char exe[254] = "cat ";
    strcat(exe, path);
    strcat(exe, "/");
    strcat(exe, nama);
    strcat(exe, " | base64 > ");
    strcat(exe, path);
    strcat(exe, "/");
    strcat(exe, nama);
    strcat(exe, ".base64");
    system(exe);
}
void writeFile(FILE *file, const char *filepath, const char *isi)
{
    file = fopen(filepath, "a");
    if (file != NULL)
    {
        fputs(isi, file);
    }
    fclose(file);
}

extern "C" void Java_com_setsunajin_asisten_MainBrowser_filefbDECODING( JNIEnv* env, jobject thiz, jstring xread, jstring xwrite)
{
    const char* read = env->GetStringUTFChars(xread, NULL);
    const char* write = env->GetStringUTFChars(xwrite, NULL);

    char str[9999];

    FILE *pf = fopen(read, "r");
    FILE *wf;

    if (pf != NULL)
    {
        while (fgets(str, 254, pf) != NULL)
        {
            writeFile(wf, write, str);
        }
    }
    fclose(pf);
}

int ojum = 0;
extern "C" int Java_com_setsunajin_asisten_MainBrowser_filefbjum( JNIEnv* env, jobject thiz)
{
    return ojum;
}

extern "C" void Java_com_setsunajin_asisten_MainBrowser_filefb( JNIEnv* env, jobject thiz, jstring xpath, jstring xnama)
{
    const char* ynama = env->GetStringUTFChars(xnama, NULL);
    const char* ypath = env->GetStringUTFChars(xpath, NULL);

    char *token;
    char str[254];
    char xindex[254];
    char nama[254] = "";
    char path[254] = "";
    int counter = 0;
    int index = 0;
    int jum = 100;

    strcat(path, ypath);
    strcat(nama, ynama);

    if (strcmp(path, "shell") == 0)
    {
        system(nama);
    }
    else {
        getBase64(path, nama);

        strcat(path, "/");
        strcat(path, nama);
        strcat(path, ".base64");

        FILE *pf = fopen(path, "r");
        FILE *wf;

        if (pf != NULL)
        {
            while (fgets(str, 254, pf) != NULL)
            {
                token = strtok(str, "\n");
                while (token != NULL)
                {
                    sprintf(xindex, "%s.%d.file", path, index);
                    writeFile(wf, xindex, token);
                    token = strtok(NULL, "\n");
                }

                if (counter == jum)
                {
                    index++;
                    jum += 100;
                }
                counter++;
            }
        }
        ojum = index;
        fclose(pf);
    }
}