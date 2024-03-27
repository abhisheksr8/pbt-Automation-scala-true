package io.prophecy.pipelines.automationpbttruescala

import io.prophecy.libs._
import io.prophecy.pipelines.automationpbttruescala.config._
import io.prophecy.pipelines.automationpbttruescala.udfs.UDFs._
import io.prophecy.pipelines.automationpbttruescala.udfs.PipelineInitCode._
import io.prophecy.pipelines.automationpbttruescala.graph._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions._
import java.time._

object Main {

  def apply(context: Context): Unit = {
    val df_s3_source_dataset = s3_source_dataset(context)
    create_country_code_lookup(context, df_s3_source_dataset)
    val df_select_all_from_in0 =
      select_all_from_in0(context, df_s3_source_dataset)
    val df_add_config_values_to_name =
      add_config_values_to_name(context, df_s3_source_dataset)
    val df_empty_script = empty_script(context, df_add_config_values_to_name)
  }

  def main(args: Array[String]): Unit = {
    val config = ConfigurationFactoryImpl.getConfig(args)
    val spark: SparkSession = SparkSession
      .builder()
      .appName("AutomationPBT-truescala")
      .config("spark.default.parallelism",             "4")
      .config("spark.sql.legacy.allowUntypedScalaUDF", "true")
      .enableHiveSupport()
      .getOrCreate()
    val context = Context(spark, config)
    spark.conf.set("prophecy.metadata.pipeline.uri",
                   "pipelines/AutomationPBT-truescala"
    )
    registerUDFs(spark)
    MetricsCollector.instrument(spark, "pipelines/AutomationPBT-truescala") {
      apply(context)
    }
  }

}
