from pystrich.ean13 import EAN13Encoder
import os
import sys


def EAN13Code(code_type, code, path):
    bar_code = "0{0}{1:08d}".format(code_type, code)
    encoder = EAN13Encoder(bar_code)
    bar_code_path = os.path.join(path, "{}.png".format(bar_code))
    encoder.save(filename=bar_code_path, bar_width=3)
    return bar_code_path


if __name__ == '__main__':
    a = []
    for i in range(1, len(sys.argv)):
        a.append(sys.argv[i])
    print(EAN13Code(a[0], int(a[1]), a[2]))
