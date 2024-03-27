package io.prophecy.pipelines.automationpbttruescala.graph

import io.prophecy.libs._
import io.prophecy.pipelines.automationpbttruescala.config.Context
import io.prophecy.pipelines.automationpbttruescala.udfs.UDFs._
import io.prophecy.pipelines.automationpbttruescala.udfs.PipelineInitCode._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions._
import java.time._

object create_country_code_lookup {

  def apply(context: Context, in0: DataFrame): Unit =
    createLookup("LookupTest",
                 in0,
                 context.spark,
                 List("customer_id", "email"),
                 "country_code"
    )

}
