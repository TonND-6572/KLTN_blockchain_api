CQL="DROP keyspace IF EXISTS transportation_blockchain;
CREATE KEYSPACE transportation_blockchain IF EXISTS WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '3'} AND durable_writes = true;"

until echo $CQL | cqlsh; do
  echo "cqlsh: Cassandra is unavailable to initialize - will retry later"
  sleep 2
done &

# cqlsh cassandra -e "CREATE KEYSPACE IF NOT EXISTS transportation_blockchain WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};"
# exec /-dockerentrypoint.sh "$@"