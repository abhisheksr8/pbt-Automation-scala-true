package io.prophecy.pipelines.automationpbttruescala.config

import org.apache.spark.sql.SparkSession
case class Context(spark: SparkSession, config: Config)
