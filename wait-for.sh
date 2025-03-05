#!/usr/bin/env bash
# wait-for.sh: aguarda até que um host e porta estejam disponíveis.
# Uso: ./wait-for.sh host porta comando...

if [ "$#" -lt 3 ]; then
  echo "Uso: $0 host porta comando..."
  exit 1
fi

host="$1"
port="$2"
shift 2

echo "Aguardando $host:$port ficar disponível..."
while ! nc -z "$host" "$port"; do
  sleep 2
done
echo "$host:$port está disponível."
exec "$@"
